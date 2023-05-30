package db;

import com.google.common.collect.Maps;
import model.User;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    public static User findUserByEmail(String email) {
        return users.get(email);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
