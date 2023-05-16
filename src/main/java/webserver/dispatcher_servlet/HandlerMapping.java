package webserver.dispatcher_servlet;

import http.request.HttpRequest;

public interface HandlerMapping {

    Servlet getHandler(HttpRequest request);
}
