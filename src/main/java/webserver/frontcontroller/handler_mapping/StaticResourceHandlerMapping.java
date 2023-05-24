package webserver.frontcontroller.handler_mapping;

import http.request.HttpRequest;
import webserver.frontcontroller.controller.Handler;
import webserver.frontcontroller.controller.StaticResourceHandler;

public class StaticResourceHandlerMapping implements HandlerMapping {

    @Override
    public Handler getHandler(HttpRequest request) {
        String path = request.getRequestLine().getRequestURI().getPath();
        return new StaticResourceHandler(path);
    }
}
