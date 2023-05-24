package servlet.controller;

import webserver.util.HttpResponseUtils;

import java.util.Map;

public class LoginFormController implements Controller {
    @Override
    public String process(Map<String, String> parameters, HttpResponseUtils httpResponse) {
        return "user/login";
    }
}
