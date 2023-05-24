package servlet.controller;

import java.util.Map;

public class IndexController implements Controller {

    @Override
    public String process(Map<String, String> parameters) {
        return "index";
    }
}
