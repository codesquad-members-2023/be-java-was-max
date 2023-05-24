package webserver.frontcontroller.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface RequestMappingController {

    String process(HttpRequest request, HttpResponse response);
}
