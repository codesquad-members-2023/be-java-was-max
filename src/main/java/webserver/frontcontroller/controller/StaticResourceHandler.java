package webserver.frontcontroller.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.frontcontroller.Model;

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
