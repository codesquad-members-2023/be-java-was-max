package db;

import model.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionDB {
    private static final Map<UUID, Object> sessions = new HashMap<>();


    public static UUID put(User user) {
        UUID uuid = UUID.randomUUID();
        sessions.put(uuid, user);
        return uuid;
    }

    public static User get(String session) {
        UUID uuid = UUID.fromString(session.substring(4));
        return (User) sessions.get(uuid);
    }

    public static boolean containsKey(String session) {
        if (session.isBlank()) {
            return false;
        }
        if (session.contains(";")) {
            String[] split1 = session.split(";");
            String sid = Arrays.stream(split1)
                    .map(s -> s.split("="))
                    .filter(a -> a[0].equals("sid"))
                    .map(a -> a[1])
                    .findFirst().orElse("");
            return sessions.containsKey(UUID.fromString(sid));
        } else {
            String[] split1 = session.split("=");
            return split1[0].equals("sid") && sessions.containsKey(UUID.fromString(split1[1]));
        }
    }
}
