package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public class RequestHandler implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;
    private final UserController userController;
    private final Request request;
    private final Response response;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.userController = new UserController();
        this.request = new Request();
        this.response = new Response();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

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

            // Request Line에서 요청된 파일의 경로, 확장자, 컨텐트 타입을 추출한다
            String requestedURI = requestHeader[1];
            String extension = requestedURI.substring(requestedURI.lastIndexOf(".") + 1);

            if (requestedURI.contains("user/create")) {
                logger.debug("회원가입 " + requestedURI);

                try {
                    URI uri = new URI(requestedURI);
                    String query = uri.getQuery();
                    Map<String, String> queryMap = request.parseQuery(query);
                    userController.join(queryMap);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

            ContentType mediaType; // enum
            String path = "";
            String contentType = "";

            for (ContentType type : ContentType.values()) {
                if (type.getExtension().equals(extension)) {
                    mediaType = type;
                    contentType = mediaType.getMimeType();
                    path = mediaType.getPath() + requestedURI;
                    break;
                }
            }

            // 경로에 해당하는 파일을 읽는다
            // TODO toPath()가 꼭 필요할지 고민해보기
            byte[] body = Files.readAllBytes(new File(path).toPath());

            // 요청에 대한 Request Message를 전송한다
            DataOutputStream dos = new DataOutputStream(out);
            response.response200Header(dos, body.length, contentType);
            response.responseBody(dos, body);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
