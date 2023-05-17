package controller;

import http.HttpBody;
import http.request.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    public static final String MAIN_PAGE = "/index.html";
    public static final String USER_SAVE_URL = "/user/create";

    public String requestMapping(HttpRequest httpRequest) {
        if (httpRequest.getRequestLine().getUrl().equals("/")) {
            return MAIN_PAGE;
        }

        if (httpRequest.getRequestLine().getUrl().equals(USER_SAVE_URL)) {
            return signUp(httpRequest);
        }

        return httpRequest.getRequestLine().getUrl();
    }

    private String signUp(HttpRequest httpRequest) {
        HttpBody httpBody = httpRequest.getBody();
        User user = new User(httpBody.findValueByKey("userId"), httpBody.findValueByKey("password"), httpBody.findValueByKey("name"), httpBody.findValueByKey("email"));
        LOGGER.debug("Created : {}", user);
        return "redirect:/";
    }
}
