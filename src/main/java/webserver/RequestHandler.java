package webserver;

import model.Line;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.HttpRequestUtils;
import webserver.util.HttpResponseUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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
            Line requestLine = HttpRequestUtils.parseLine(line);

            if (requestLine.getMethod().equals("GET") && requestLine.getQueryMap() != null) {
                User user = new User(requestLine.getQueryMap());
                log.debug("user : {}", user);
            }

            // request 마지막에 빈 공백 문자열이 들어오니 그때까지 반복
            while (!line.equals("")) {
                line = br.readLine();
                log.debug("header : {}", line);
            }

            DataOutputStream dos = new DataOutputStream(out);
            // https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#readAllBytes-java.nio.file.Path-
            byte[] body = Files.readAllBytes(new File(requestLine.separateAbsolutePath()).toPath());

            HttpResponseUtils.responseBody(dos, body, requestLine);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
