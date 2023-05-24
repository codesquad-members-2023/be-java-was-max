package webserver.handler;

import static http.common.header.ResponseHeaderType.SET_COOKIE;
import static http.parser.HttpRequestParser.parseHttpRequest;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.component.ResponseHeader;
import http.session.Cookie;
import http.session.HttpSession;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.frontcontroller.FrontControllerServlet;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final FrontControllerServlet frontController;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.frontController = new FrontControllerServlet();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest request = parseHttpRequest(br);
            HttpResponse response = new HttpResponse();
            logHttpRequest(request);

            frontController.service(request, response);

            responseCookie(request, response);
            responseHeader(dos, response);
            responseBody(dos, response);
            logHttpResponse(response);
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
        } else if (request.getSessionId() != null) {
            String sessionIdCookie = createSessionIdCookieString(request.getSessionId());
            response.getResponseHeader().put(SET_COOKIE, sessionIdCookie);
        }
    }

    private String createSessionIdCookieString(String sid) {
        return HttpSession.createSessionIdCookie(sid).stream()
            .map(Cookie::toString)
            .collect(Collectors.joining(";"));
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

    private void logHttpRequest(HttpRequest request) {
        logger.debug("requestLine : {}", request.getRequestLine());
        request.getRequestHeader().keySet().forEach(headerType ->
            logger.debug("requestHeader : {}: {}", headerType.value(), request.getRequestHeader().get(headerType)));
        logger.debug("queryString : {}", request.getQueryString().getFormattedQueryString());
        logger.debug("messageBody : {}", request.getMessageBody().toString());
    }

    private void logHttpResponse(HttpResponse response) {
        logger.debug("statusLine : {}", response.getStatusLine());
        ResponseHeader responseHeader = response.getResponseHeader();
        response.getResponseHeader().keySet().forEach(headerType ->
            logger.debug("requestHeader : {}: {}", headerType.value(), response.getResponseHeader().get(headerType)));
    }
}
