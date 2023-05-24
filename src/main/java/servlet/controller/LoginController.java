package servlet.controller;

import db.Database;
import model.User;

import java.util.Map;
import java.util.Optional;

public class LoginController implements Controller {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";

    @Override
    public String process(Map<String, String> parameters) {
        String userId = parameters.get(USER_ID);
        String password = parameters.get(PASSWORD);

        Optional<User> findUser = Database.findAll().stream()
                .filter(user -> user.getUserId().equals(userId))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();

        if (findUser.isEmpty()) {
            return "user/login_failed";
        }

        return "redirect:/";
    }
}
