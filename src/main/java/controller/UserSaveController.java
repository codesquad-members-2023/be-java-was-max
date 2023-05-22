package controller;

import db.UserDatabase;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static controller.FrontController.REDIRECT_URL_PREFIX;

public class UserSaveController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSaveController.class);
    private static final String USER_LOGIN_PAGE = "/user/login.html";
    @Override
    public String process(HttpRequest request, HttpResponse response) {
        RequestBody requestBody = request.getBody();
        User user = new User(requestBody.findValueByKey("userId"),
                             requestBody.findValueByKey("password"),
                             requestBody.findValueByKey("name"),
                             requestBody.findValueByKey("email"));
        UserDatabase.addUser(user);

        LOGGER.debug("--- Created : {} ---", user);
        return REDIRECT_URL_PREFIX + USER_LOGIN_PAGE;
    }
}
