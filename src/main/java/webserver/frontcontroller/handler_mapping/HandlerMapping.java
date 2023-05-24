package webserver.frontcontroller.handler_mapping;

import http.request.HttpRequest;
import webserver.frontcontroller.controller.Handler;

public interface HandlerMapping {

    Handler getHandler(HttpRequest request);
}
