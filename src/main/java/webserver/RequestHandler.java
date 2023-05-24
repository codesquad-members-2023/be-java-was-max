package webserver;

import java.io.*;
import java.net.Socket;

import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpRequest;
import servlet.DispatcherServlet;

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
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse();
            DataOutputStream dos = new DataOutputStream(out);

            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.run(httpRequest, httpResponse);
            responseHeader(dos, httpResponse);
            responseBody(dos, httpResponse.getResponseBody());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.getStatusLine() + "\r\n");
            if (httpResponse.getContentType().isPresent()) {
                dos.writeBytes("Content-Type: " + httpResponse.getContentType().get() + ";charset=utf-8\r\n");
            }
            if (httpResponse.getContentLength().isPresent()) {
                dos.writeBytes("Content-Length: " + httpResponse.getContentLength().get() + ";charset=utf-8\r\n");
            }
            if (httpResponse.getLocation().isPresent()) {
                dos.writeBytes("Location: " + httpResponse.getLocation().get() + "\r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
