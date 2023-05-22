package session;

import java.util.UUID;

public final class SessionUtil {

    private SessionUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static String createSessionId() {
        return UUID.randomUUID().toString();
    }
}
