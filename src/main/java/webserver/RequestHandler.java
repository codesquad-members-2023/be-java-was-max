package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = new HttpRequest(in);
            DataOutputStream dos = new DataOutputStream(out);
//            String absolutePath = Paths.get("").toAbsolutePath().toString();
            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.run(httpRequest);
            // TODO: new File().toPath()와 Paths.get()이 무슨 차이인지 알아보기
            byte[] body = Files.readAllBytes(new File(absolutePath
                    + "/src/main/resources" + httpRequest.getPathPrefix() + httpRequest.getPath()).toPath());
//            byte[] body = Files.readAllBytes(Paths.get(absolutePath
//                  + "/src/main/resources" + httpRequest.getContentType() + httpRequest.getUrl()));
            response200Header(dos, body, httpRequest);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, byte[] body, HttpRequest httpRequest) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + httpRequest.getContentType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
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
