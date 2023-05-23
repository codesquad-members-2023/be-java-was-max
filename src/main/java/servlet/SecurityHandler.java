package servlet;

import db.SessionDB;
import servlet.domain.HttpRequest;

import java.util.ArrayList;
import java.util.List;

public class SecurityHandler {
    private static final List<String> permitList = init();

    private static List<String> init() {
        List<String> permitList = new ArrayList<>();
        permitList.add("/");
        permitList.add("/index.html");
        permitList.add("/user/form.html");
        permitList.add("/user/create");
        permitList.add("/user/login.html");
        permitList.add("/user/list");
        return permitList;
    }

    public static boolean isPermit(HttpRequest httpRequest) {
        String url = httpRequest.getUrl();
        if (url.startsWith("/css") || url.startsWith("/js")) {
            return true;
        }
        if (permitList.contains(url)) {
            return true;
        }
        return SessionDB.containsKey(httpRequest.getSession());
    }
}
