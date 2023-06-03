package webserver.frontcontroller.controller;

import webserver.frontcontroller.Model;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class StaticResourceHandler implements Handler {

    private final String path;

    public StaticResourceHandler(String path) {
        this.path = path;
    }

    @Override
    public String process(HttpRequest request, HttpResponse response, Model model) {
        return path;
    }
}
