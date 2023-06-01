package webserver.frontcontroller.handler_mapping;

import webserver.frontcontroller.controller.Handler;
import webserver.frontcontroller.controller.StaticResourceHandler;
import webserver.http.request.HttpRequest;

public class StaticResourceHandlerMapping implements HandlerMapping {

    @Override
    public Handler getHandler(HttpRequest request) {
        String path = request.getRequestLine().getRequestURI().getPath();
        return new StaticResourceHandler(path);
    }
}
