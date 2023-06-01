package webserver.http.session;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class SessionContainer {

    private static final ConcurrentHashMap<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public static boolean containsHttpSession(String sid) {
        return sessions.containsKey(sid);
    }

    public static HttpSession getSession(String sid) {
        if (sid == null || sessions.get(sid) == null) {
            String newSessionId = new SessionIdGenerator(new Random()).generateSessionId();
            HttpSession newSession = new HttpSession(newSessionId);
            sessions.put(newSessionId, newSession);
            return newSession;
        }
        return sessions.get(sid);
    }

    public static HttpSession get(String sid) {
        if (sid == null) {
            return null;
        }
        return sessions.get(sid);
    }

    public static void removeSession(String sid) {
        sessions.remove(sid);
    }
}
