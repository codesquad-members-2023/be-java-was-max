package webserver.frontcontroller.adapter;

import webserver.frontcontroller.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface MyHandlerAdapter {

    boolean supports(Object handler);

    ModelAndView handle(HttpRequest request, HttpResponse response, Object handler);

}
