package servlet.controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.util.HttpRequestUtils;
import webserver.util.HttpResponseUtils;

import java.util.Map;

public class UserSaveController implements Controller {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public String process(HttpRequestUtils httpRequest, HttpResponseUtils httpResponse) {
        Map<String, String> parameters = httpRequest.getParameters();
        String userId = parameters.get(USER_ID);
        String password = parameters.get(PASSWORD);
        String name = parameters.get(NAME);
        String email = parameters.get(EMAIL);

        User user = new User(userId, password, name, email);
        Database.addUser(user);

        logger.debug("생성한 유저 : {}", user);

        return "redirect:/";
    }
}
