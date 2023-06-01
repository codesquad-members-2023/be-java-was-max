package webserver.frontcontroller.handler_mapping;

import webserver.frontcontroller.controller.Handler;
import webserver.http.request.HttpRequest;

public interface HandlerMapping {

    Handler getHandler(HttpRequest request);
}
