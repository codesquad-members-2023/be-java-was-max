package controller;

import model.User;
import request.HttpRequest;

import java.util.Map;

public class UserController {
    public static final String MAIN_PAGE = "/index.html";

    public String requestMapping(HttpRequest httpRequest) {
        if (httpRequest.getUrl().equals("/")) {
            return MAIN_PAGE;
        }

        if (httpRequest.getUrl().equals("/user/create")) {
            return signUp(httpRequest);
        }

        return httpRequest.getUrl();
    }

    private String signUp(HttpRequest httpRequest) {
        Map<String, String> queryStrings = httpRequest.getQueryString();
        User user = new User(queryStrings.get("userId"), queryStrings.get("password"), queryStrings.get("name"), queryStrings.get("email"));
        return "redirect:/";
    }
}
