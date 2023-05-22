package session;

import com.google.common.collect.Maps;

import java.util.Map;

public class SessionDatabase {
    private static final Map<String, String> sessions = Maps.newHashMap();

    public static String saveSession(String userId) {
        String sessionId = SessionUtil.createSessionId();
        sessions.put(sessionId, userId);
        return sessionId;
    }
}
