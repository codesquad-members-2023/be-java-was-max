package servlet;

import db.SessionDB;
import model.User;
import servlet.domain.HttpResponse;
import servlet.domain.HttpResponseStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewResolver {

    public static final String SRC_MAIN_RESOURCES = "src/main/resources";
    public static final String PREFIX = "/";

    private ViewResolver() {
    }

    public static HttpResponse resolve(String result) throws IOException {
        String body;
        String session = null;
        if (result.contains("sid=")) {
            int index = result.indexOf("sid=");
            session = result.substring(index);
            result = result.substring(0, index - 1);
        }

        if (result.startsWith(PREFIX)) {
            Path path = Paths.get(SRC_MAIN_RESOURCES + result);
            body = Files.readString(path);
        } else {
            body = result;
        }
        return session == null ? new HttpResponse(HttpResponseStatus.OK, body) :
                new HttpResponse(HttpResponseStatus.OK, loginSession(body, session), session);
    }

    private static String loginSession(String body, String session) {
        User user = SessionDB.get(session);
        return body.replace("로그인", user.getName());
    }
}
