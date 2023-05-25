package webserver;

import java.io.*;
import java.net.Socket;

import db.Database;
import http.response.HttpResponse;
import model.User;
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

            User user = new User("jianId", "1234", "jian", "jian@gmail.com");
            Database.addUser(user);
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
            if (httpResponse.get("Content-Type").isPresent()) {
                dos.writeBytes("Content-Type: " + httpResponse.get("Content-Type").get() + ";charset=utf-8\r\n");
            }
            if (httpResponse.get("Content-Length").isPresent()) {
                dos.writeBytes("Content-Length: " + httpResponse.get("Content-Length").get() + ";charset=utf-8\r\n");
            }
            if (httpResponse.get("Location").isPresent()) {
                dos.writeBytes("Location: " + httpResponse.get("Location").get() + "\r\n");
            }
            if (httpResponse.get("Set-Cookie").isPresent()) {
                dos.writeBytes("Set-Cookie: " + httpResponse.get("Set-Cookie").get() + "\r\n");
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
