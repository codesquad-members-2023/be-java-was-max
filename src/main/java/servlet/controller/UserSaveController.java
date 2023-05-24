package servlet.controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.Map;

public class UserSaveController implements Controller {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public String process(Map<String, String> parameters) {
        String userId = parameters.get(USER_ID);
        String password = parameters.get(PASSWORD);
        String name = parameters.get(NAME);
        String email = parameters.get(EMAIL);

        User user = new User(userId, password, name, email);

        logger.debug("생성한 유저 : {}", user);

        return "redirect:/";
    }
}
