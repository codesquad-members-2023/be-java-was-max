package webserver.dispatcher_servlet;

import http.request.HttpRequest;

public interface HandlerMapping {

    Handler getHandler(HttpRequest request);
}
