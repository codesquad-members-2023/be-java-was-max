package webserver.util;

import db.Database;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.component.RequestParameter;

public final class RequestHandlerUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerUtil.class);
    private static final String STATIC_PATH = "src/main/resources/static";
    private static final String TEMPLATES_PATH = "src/main/resources/templates";

    private RequestHandlerUtil() {

    }

    public static byte[] readFile(String path) {
        try {
            logger.debug("path : {}", path);
            return findFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isStaticResource(String path) {
        // static 디렉토리 탐색
        File file = new File(STATIC_PATH + path);
        if (file.exists()) {
            return true;
        }

        // template 디렉토리 탐색
        file = new File(TEMPLATES_PATH + path);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static byte[] findFile(String requestURI) throws IOException {
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

    public static void requestSignUp(RequestParameter requestParameter) {
        String userId = requestParameter.get("userId");
        String password = requestParameter.get("password");
        String name = requestParameter.get("name");
        String email = requestParameter.get("email");
        User user = new User(userId, password, name, email);
        logger.debug("user : {}", user);
        Database.addUser(user);
    }
}
