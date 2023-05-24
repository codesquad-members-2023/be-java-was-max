package servlet.controller;

import webserver.util.HttpResponseUtils;

import java.util.Map;

public class IndexController implements Controller {

    @Override
    public String process(Map<String, String> parameters, HttpResponseUtils httpResponse) {
        return "index";
    }
}
