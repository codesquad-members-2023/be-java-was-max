package db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.User;

public class UserRepository {
	private static Map<String, User> users = new HashMap<>();

	public void addUser(User user) {
		users.put(user.getUserId(), user);
	}

	public User findUserById(String userId) {
		return users.get(userId);
	}

	public Collection<User> findAll() {
		return users.values();
	}
}
