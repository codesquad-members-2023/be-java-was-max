package webserver;

import controller.FrontController;
import http.HttpHeader;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.LOCATION;
import static com.google.common.net.HttpHeaders.SET_COOKIE;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private final FrontController frontController = new FrontController();
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest();
            HttpResponse response = new HttpResponse();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)); // HTTP Request 읽기
            String startLine = reader.readLine(); // request line
            RequestLine requestLine = new RequestLine(startLine);
            request.setRequestLine(requestLine);
            LOGGER.debug("--- HTTP Request : {} ---", requestLine);

            String line;
            List<String> headers = new ArrayList<>();
            while (!(line = reader.readLine()).isEmpty()) { // request headers
                headers.add(line);
                LOGGER.debug("{}", line);
            }
            HttpHeader header = new HttpHeader(headers);
            request.setHeaders(header);

            if (header.contains(CONTENT_LENGTH)) { // request body
                int contentLength = Integer.parseInt(header.findFieldByName(CONTENT_LENGTH));
                char[] requestBody = new char[contentLength];
                reader.read(requestBody, 0, contentLength);
                String decode = URLDecoder.decode(String.valueOf(requestBody), StandardCharsets.UTF_8);
                RequestBody body = new RequestBody(decode);
                request.setBody(body);
                LOGGER.debug("Request Body : {}", decode);
            }

            DataOutputStream dos = new DataOutputStream(out);

            frontController.service(request, response);

            responseHeader(dos, response);
            if (response.getBody() != null) {
                responseBody(dos, response.getBody());
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes(response.getStatusLine() + "\r\n");
            HttpHeader headers = response.getHeaders();
            if (headers.contains(CONTENT_TYPE)) {
                dos.writeBytes(CONTENT_TYPE + ": " + headers.findFieldByName(CONTENT_TYPE) + "\r\n");
            }
            if (headers.contains(LOCATION)) {
                dos.writeBytes(LOCATION + ": " + headers.findFieldByName(LOCATION) + "\r\n");
            }
            if (headers.contains(SET_COOKIE)) {
                dos.writeBytes(SET_COOKIE + ": " + headers.findFieldByName(SET_COOKIE) + "\r\n");
            }
            if (response.getBody() != null) {
                dos.writeBytes(CONTENT_LENGTH + ": " + response.getBody().length + "\r\n");
            }
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
