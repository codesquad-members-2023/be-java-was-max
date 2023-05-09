package webserver.request;

import db.Database;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RequestHandlerUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerUtil.class);
    private static final String STATIC_PATH = "src/main/resources/static";
    private static final String TEMPLATES_PATH = "src/main/resources/templates";

    private RequestHandlerUtil() {

    }

    public static String readFile(RequestLine requestLine) {
        try {
            String requestURI = requestLine.getRequestURI();
            logger.debug("requestURI : {}", requestURI);

            byte[] bytes = findFile(requestURI);

            BufferedReader br =
                new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            return sb.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isStaticResource(String requestURI) throws URISyntaxException {
        // static 디렉토리 탐색
        File file = new File(STATIC_PATH + requestURI);
        if (file.exists()) {
            return true;
        }

        // template 디렉토리 탐색
        file = new File(TEMPLATES_PATH + requestURI);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    private static byte[] findFile(String requestURI) throws IOException {
        // static 디렉토리 탐색
        File file = new File(STATIC_PATH + requestURI);
        if (file.exists()) {
            return Files.readAllBytes(file.toPath());
        }

        // template 디렉토리 탐색
        file = new File(TEMPLATES_PATH + requestURI);
        if (file.exists()) {
            return Files.readAllBytes(file.toPath());
        }
        return null;
    }

    public static void requestSignUp(RequestLine requestLine) {
        String userId = requestLine.getRequestParameter().get("userId");
        String password = requestLine.getRequestParameter().get("password");
        String name = requestLine.getRequestParameter().get("name");
        String email = requestLine.getRequestParameter().get("email");
        User user = new User(userId, password, name, email);
        logger.debug("user : {}", user);
        Database.addUser(user);
    }
}
