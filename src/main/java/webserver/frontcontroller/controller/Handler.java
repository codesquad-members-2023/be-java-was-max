package webserver.frontcontroller.controller;

import webserver.frontcontroller.Model;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Handler {

    String process(HttpRequest request, HttpResponse response, Model model);
}
