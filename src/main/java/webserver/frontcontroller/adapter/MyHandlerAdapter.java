package webserver.frontcontroller.adapter;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.frontcontroller.ModelAndView;

public interface MyHandlerAdapter {

    boolean supports(Object handler);

    ModelAndView handle(HttpRequest request, HttpResponse response, Object handler);

}
