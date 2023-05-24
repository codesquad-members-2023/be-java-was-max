package webserver.util;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserSession {
    private static final String COOKIE_NAME = "sid";
    private static final Map<String, User> session = new HashMap<>();

    public static void addUser(String sessionId, User user) {
        session.put(sessionId, user);
    }

    public static User get(Map<String, String> cookies) {
        if (!cookies.containsKey(COOKIE_NAME)) {
            throw new IllegalArgumentException("로그인하세요.");
        }

        String sessionId = cookies.get(COOKIE_NAME);
        if (!session.containsKey(sessionId)) {
            throw new IllegalArgumentException("로그인하세요.");
        }

        return session.get(sessionId);
    }
}
