package webserver.handler;

import static http.common.header.ResponseHeaderType.SET_COOKIE;
import static http.parser.HttpRequestParser.parseHttpRequest;
import static util.FileUtils.readFile;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.Cookie;
import http.session.HttpSession;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.frontcontroller.old.DispatcherServlet;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest request = parseHttpRequest(br);
            HttpResponse response = new HttpResponse();
            StaticResourceHandler staticResourceHandler = new StaticResourceHandler();
            logger.debug("httpRequest : {}", request);

            Optional<File> optionalStaticResource = readFile(request.getPath());

            if (optionalStaticResource.isPresent()) {
                File file = optionalStaticResource.get();
                staticResourceHandler.process(file, request, response);
            } else {
                DispatcherServlet dispatcherServlet = new DispatcherServlet();
                dispatcherServlet.doDispatch(request, response);
            }

            responseCookie(request, response);
            responseHeader(dos, response);
            responseBody(dos, response);
            logger.debug("httpResponse : {}", response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void responseCookie(HttpRequest request, HttpResponse response) {
        if (request.hasHttpSession()) {
            String sessionIdCookie = createSessionIdCookieString(request.getHttpSession().getId());
            response.getResponseHeader().put(SET_COOKIE, sessionIdCookie);
        } else if (request.getSid() != null) {
            String sessionIdCookie = createSessionIdCookieString(request.getSid());
            response.getResponseHeader().put(SET_COOKIE, sessionIdCookie);
        }
    }

    private String createSessionIdCookieString(String sid) {
        List<Cookie> cookies = HttpSession.createSessionIdCookie(sid);
        return cookies.stream().map(Cookie::toString).collect(Collectors.joining(";"));
    }

    private void responseHeader(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.getStatusLine().toString());
            dos.writeBytes(System.lineSeparator());
            dos.writeBytes(httpResponse.getResponseHeader().toString());
            dos.writeBytes(System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            byte[] messageBody = httpResponse.getMessageBody();
            dos.write(messageBody, 0, messageBody.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
