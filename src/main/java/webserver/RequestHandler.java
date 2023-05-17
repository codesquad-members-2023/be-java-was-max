package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final UserController userController;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.userController = new UserController();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            /**
             * Request 처리
             * */

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            // Request Line을 읽는다
            String requestLine = inputReader.readLine();
            logger.debug("request line : {}", requestLine);
            String[] requestHeader = requestLine.split(" ");

            // TODO 메서드 추출 후 처리
            String method = requestHeader[0];

            // Request Header를 읽고 로그를 출력한다
            while (!requestLine.equals("")) {
                requestLine = inputReader.readLine();
                logger.debug("header : {}", requestLine);
            }

            /**
             * Response 처리
             * */
            // Request Line에서 요청된 파일의 경로, 확장자, 컨텐트 타입을 추출한다
            String requestedURI = requestHeader[1];
            String extension = requestedURI.substring(requestedURI.lastIndexOf(".") + 1);

            if (requestedURI.contains("user/create")) {
                logger.debug("회원가입 " + requestedURI);

                try {
                    URI uri = new URI(requestedURI);
                    String query = uri.getQuery();

                    Map<String, String> queryMap = parseQuery(query);
                    String userId = queryMap.get("userId");
                    String password = queryMap.get("password");
                    String name = queryMap.get("name");
                    String email = queryMap.get("email");

                    userController.join(userId, password, name, email);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

            MediaType mediaType; // enum
            String path = "";
            String contentType = "";

            for (MediaType type : MediaType.values()) {
                if (type.getExtension().equals(extension)) {
                    mediaType = type;
                    contentType = mediaType.getContentType();
                    path = mediaType.getPath() + requestedURI;
                    break;
                }
            }

            // 경로에 해당하는 파일을 읽는다
            // TODO toPath()가 꼭 필요할지 고민해보기
            byte[] body = Files.readAllBytes(new File(path).toPath());

            // 요청에 대한 Request Message를 전송한다
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
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

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> queryMap = new HashMap<>();

        if (query != null && !query.isEmpty()) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    queryMap.put(key, value);
                }
            }
        }

        return queryMap;
    }
}
