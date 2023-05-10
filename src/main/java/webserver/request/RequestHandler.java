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
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.response.Response;
import webserver.response.StatusLine;

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
            byte[] messageBody = new byte[0];
            if (RequestHandlerUtil.isStaticResource(httpRequest.getRequestLine().getRequestURI())) {
                messageBody = RequestHandlerUtil.readFile(httpRequest.getRequestLine());
            } else if (httpRequest.getRequestLine().getRequestURI().equals("/user/create")) {
                RequestHandlerUtil.requestSignUp(httpRequest.getRequestLine());
                RequestParameter requestParameter = new RequestParameter(new HashMap<>());
                RequestLine requestLine =
                    new RequestLine("GET", "/index.html", requestParameter, "HTTP/1.0");
                messageBody = RequestHandlerUtil.readFile(requestLine);
            }

            // Reponse 생성
            Response response =
                new Response(new StatusLine("HTTP/1.1", 200, "OK"), new HashMap<>(), messageBody);
            // 어떤 정적 리소스 타입(html, css, js...)인지 확인해야함
            // 일단 1차적으로 파일의 확장자를 통하여 contentType을 선택하고
            // 1차 기준에서 없다면 Accept 헤더를 본다.
            String contentType = "text/html;charset=utf-8";
            if (httpRequest.getRequestLine().getRequestURI().endsWith("html")) {
                contentType = "text/html;charset=utf-8";
            } else if (httpRequest.getRequestLine().getRequestURI().endsWith("css")) {
                contentType = "text/css;charset=utf-8";
            } else if (httpRequest.getRequestLine().getRequestURI().endsWith("js")) {
                contentType = "application/javascript;charset=utf-8";
            } else if (httpRequest.getRequestLine().getRequestURI().endsWith("ico")) {
                contentType = "image/vnd.microsoft.icon";
            } else if (httpRequest.getRequestLine().getRequestURI().endsWith("png")) {
                contentType = "image/png";
            } else if (httpRequest.getRequestLine().getRequestURI().endsWith("jpg")) {
                contentType = "image/jpeg";
            } else if (httpRequest.getRequestLine().getRequestURI().endsWith("ttf")) {
                contentType = "font/ttf";
            } else if (httpRequest.getRequestLine().getRequestURI().endsWith("woff")) {
                contentType = "font/woff";
            }

            logger.debug("contentType : {}", contentType);
            response.addHeader("Content-Type", contentType);
            response.addHeader("Content-Length",
                String.valueOf(response.getMessageBody().length));
            logger.debug(response.getStatusLine().toString());
            logger.debug(response.getHeader().toString());
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, response);
            responseBody(dos, response.getMessageBody());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, Response response) {
        try {
            Map<String, String> header = response.getHeader();
            StringBuilder sb = new StringBuilder();
            sb.append(response.getStatusLine()).append("\r\n");
            for (String key : header.keySet()) {
                sb.append(key).append(": ").append(header.get(key)).append("\r\n");
            }
            sb.append("\r\n");
            dos.writeBytes(sb.toString());
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
