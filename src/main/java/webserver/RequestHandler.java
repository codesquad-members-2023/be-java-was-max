package webserver;

import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.ContentType;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)); // HTTP Request 읽기
            String startLine = reader.readLine(); // start line
            HttpRequest httpRequest = new HttpRequest(startLine);
            LOGGER.debug("HTTP Request : {}", httpRequest);

            String line;
            while (!(line = reader.readLine()).equals("")) {
                LOGGER.debug("Header : {}", line);
            }

            DataOutputStream dos = new DataOutputStream(out);

            String url = userController.requestMapping(httpRequest);
            if (url.startsWith("redirect")) {
                response302Header(dos, url);
                return;
            }

            byte[] body = findFilePath(url);
            response200Header(dos, body.length, findContentType(url));
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

    private byte[] findFilePath(String url) throws IOException {
        // Static
        if (url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".ico") || url.endsWith(".png") || url.endsWith(".jpeg") || url.endsWith(".jpg")) { // 파일의 확장자로 구분
            return Files.readAllBytes(new File("src/main/resources/static" + url).toPath());
        }
        // Templates
        return Files.readAllBytes(new File("src/main/resources/templates" + url).toPath());
    }

    private String findContentType(String url) {
        String extension = url.substring(url.lastIndexOf("."));
        if (extension.equals(".js")) {
            return ContentType.JS.getType();
        }
        if (extension.equals(".css")) {
            return ContentType.CSS.getType();
        }
        if (extension.equals(".png")) {
            return ContentType.PNG.getType();
        }
        if (extension.equals(".jpeg") || extension.equals(".jpg")) {
            return ContentType.JPEG.getType();
        }
        if (extension.equals(".ttf") || extension.equals(".woff")) {
            return ContentType.FONT.getType();
        }
        return ContentType.HTML.getType();
    }
}
