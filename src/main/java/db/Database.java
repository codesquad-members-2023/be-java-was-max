package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class Database {
    private static final Map<String, User> users = Maps.newConcurrentMap();

    public static String addUser(User user) {
        users.put(user.getUserId(), user);
        return user.getUserId();
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
