package db;

import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionDB {
    private static final Map<UUID, Object> session = new HashMap<>();


    public static void put(UUID uuid, User user) {
        session.put(uuid, user);
    }
}
