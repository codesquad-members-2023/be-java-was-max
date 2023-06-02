package servlet.controller;

import db.Database;
import model.User;
import webserver.util.HttpResponseUtils;
import webserver.util.UserSession;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class LoginController implements Controller {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String COOKIE_NAME = "sid";

    @Override
    public String process(Map<String, String> parameters, HttpResponseUtils httpResponse) {
        String userId = parameters.get(USER_ID);
        String password = parameters.get(PASSWORD);

        Optional<User> findUser = Database.findAll().stream()
                .filter(user -> user.getUserId().equals(userId))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();

        if (findUser.isEmpty()) {
            return "user/login_failed";
        }

        String uuid = UUID.randomUUID().toString();
        httpResponse.setCookie(COOKIE_NAME, uuid);
        UserSession.addUser(uuid, findUser.get());

        return "redirect:/";
    }
}
