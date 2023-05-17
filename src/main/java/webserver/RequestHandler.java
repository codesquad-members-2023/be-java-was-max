package webserver;

import controller.UserController;
import http.HttpBody;
import http.HttpHeader;
import http.HttpUtils;
import http.request.HttpRequest;
import http.request.HttpRequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private final UserController userController = new UserController();
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)); // HTTP Request 읽기
            String startLine = reader.readLine(); // start line
            HttpRequestLine httpRequestLine = new HttpRequestLine(startLine);
            httpRequest.setRequestLine(httpRequestLine);
            LOGGER.debug("--- HTTP Request : {} ---", httpRequestLine);

            String line;
            List<String> headers = new ArrayList<>();
            while (!(line = reader.readLine()).equals("")) { // headers
                headers.add(line);
                LOGGER.debug("{}", line);
            }
            HttpHeader header = new HttpHeader(headers);
            httpRequest.setHeaders(header);

            if (header.contains("Content-Length")) { // body
                int contentLength = Integer.parseInt(header.findFieldByName("Content-Length"));
                char[] requestBody = new char[contentLength];
                reader.read(requestBody, 0, contentLength);
                StringBuilder builder = new StringBuilder();
                builder.append(requestBody);
                HttpBody body = new HttpBody(builder.toString());
                httpRequest.setBody(body);
                LOGGER.debug("Request Body : {}", builder);
            }
            DataOutputStream dos = new DataOutputStream(out);

            String url = userController.requestMapping(httpRequest);
            if (url.startsWith("redirect")) {
                response302Header(dos, url);
                return;
            }

            byte[] body = HttpUtils.findFilePath(url);
            response200Header(dos, body.length, HttpUtils.findContentType(url));
            responseBody(dos, body);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND\r\n");
            dos.writeBytes("Location: " + url.substring(url.indexOf("/")) + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
