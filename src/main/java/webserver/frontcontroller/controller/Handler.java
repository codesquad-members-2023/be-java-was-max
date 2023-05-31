package webserver.frontcontroller.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.frontcontroller.Model;

public interface Handler {

    String process(HttpRequest request, HttpResponse response, Model model);
}
