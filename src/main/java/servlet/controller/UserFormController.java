package servlet.controller;

import java.util.Map;

public class UserFormController implements Controller{

    @Override
    public String process(Map<String, String> parameters) {
        return "user/form";
    }
}
