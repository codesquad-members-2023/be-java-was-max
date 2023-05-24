package servlet.controller;

import java.util.Map;

public class LoginFormController implements Controller {
    @Override
    public String process(Map<String, String> parameters) {
        return "user/login";
    }
}
