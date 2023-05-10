package webserver;

import java.io.*;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.HttpRequestUtils;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // https://docs.oracle.com/javase/8/docs/api/java/nio/charset/StandardCharsets.html
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            // 라인별로 http header 읽기
            String line = br.readLine();
            String url = HttpRequestUtils.parseUrl(line);
            String method = HttpRequestUtils.parseMethod(line);
            Map<String, String> quaryMap = HttpRequestUtils.parseQueryString(line);

            if (method.equals("GET") && quaryMap != null) {
                User user = new User(quaryMap);
                log.debug("user : {}", user);
            }

            // 어떤 스레드, 클래스에서 해당 로그를 출력하는지까지 다 출력
            log.debug("request line : {}", line);

            // request 마지막에 빈 공백 문자열이 들어오니 그때까지 반복
            while (!line.equals("")) {
                line = br.readLine();
                log.debug("header : {}", line);
            }

            DataOutputStream dos = new DataOutputStream(out);
            // https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#readAllBytes-java.nio.file.Path-
            byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + url).toPath());

            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
