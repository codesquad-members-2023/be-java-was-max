package webserver.request;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(),
            connection.getPort());

        try (BufferedReader br =
            new BufferedReader(new InputStreamReader(connection.getInputStream(), UTF_8));
            OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestFactory.createHttpRequest(br);
            logger.debug("httpRequest: {}", httpRequest);

            // 단순한 파일을 읽는 것인지 api인지 검사해야함
            String messageBody = "";
            if (RequestHandlerUtil.isStaticResource(httpRequest.getRequestLine().getRequestURI())) {
                messageBody = RequestHandlerUtil.readFile(httpRequest.getRequestLine());
            } else if (httpRequest.getRequestLine().getRequestURI().equals("/user/create")) {
                RequestHandlerUtil.requestSignUp(httpRequest.getRequestLine());
                RequestParameter requestParameter = new RequestParameter(new HashMap<>());
                RequestLine requestLine =
                    new RequestLine("GET", "/index.html", requestParameter, "HTTP/1.0");
                messageBody = RequestHandlerUtil.readFile(requestLine);
            }

            byte[] body = messageBody.getBytes();
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
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
