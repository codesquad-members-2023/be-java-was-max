package controller;

import db.UserDatabase;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.SessionDatabase;

import static controller.FrontController.ARTICLE_LIST_URL;
import static controller.FrontController.REDIRECT_URL_PREFIX;

public class UserLoginController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginController.class);
    private static final String LOGIN_FAILED_PAGE = "/user/login_failed.html";

    @Override
    public String process(final HttpRequest request, HttpResponse response) {
        RequestBody requestBody = request.getBody();

        final String userId = requestBody.findValueByKey("userId");
        final String password = requestBody.findValueByKey("password");

        final User user = UserDatabase.findUserById(userId);
        if (user == null || !user.checkPasswordMatch(password)) {
            return LOGIN_FAILED_PAGE;
        }

        String session = SessionDatabase.saveSession(userId);
        response.getHeaders().addSetCookie("SID=" + session + "; Path=/");

        LOGGER.debug("--- Login : {} ---", user);
        return REDIRECT_URL_PREFIX + ARTICLE_LIST_URL;
    }
}
