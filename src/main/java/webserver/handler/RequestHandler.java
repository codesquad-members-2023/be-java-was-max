package webserver.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.frontcontroller.FrontControllerServlet;
import webserver.http.common.ContentType;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.component.ResponseHeader;
import webserver.http.response.component.StatusLine;
import webserver.http.session.Cookie;
import webserver.http.session.HttpSession;
import webserver.util.FileUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

import static webserver.http.common.ContentType.resolve;
import static webserver.http.common.HttpStatus.OK;
import static webserver.http.common.header.EntityHeaderType.CONTENT_LENGTH;
import static webserver.http.common.header.EntityHeaderType.CONTENT_TYPE;
import static webserver.http.common.header.ResponseHeaderType.SET_COOKIE;
import static webserver.http.common.version.HttpVersion.HTTP_1_1;
import static webserver.http.parser.HttpRequestParser.parseHttpRequest;

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

            Optional<File> optionalFile = FileUtils.getFileFromPath(request.getRequestLine().getRequestURI().getPath());
            if (optionalFile.isPresent()) {
                File file = optionalFile.get();
                responseStaticResource(file, response);
            } else {
                frontController.service(request, response);
            }

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
        }
    }

    private String createSessionIdCookieString(String sid) {
        return HttpSession.createSessionIdCookie(sid).stream()
                .map(Cookie::toString)
                .collect(Collectors.joining(";"));
    }

    private void responseStaticResource(File file, HttpResponse response) throws IOException {
        byte[] messageBodyBytes = Files.readAllBytes(file.toPath());
        ContentType contentType = resolve(file.getPath());
        response.setStatusLine(new StatusLine(HTTP_1_1, OK));
        response.addHeader(CONTENT_TYPE, contentType.toString());
        response.addHeader(CONTENT_LENGTH, String.valueOf(messageBodyBytes.length));
        response.setMessageBody(messageBodyBytes);
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
