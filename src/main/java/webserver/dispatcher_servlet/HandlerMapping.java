package webserver.dispatcher_servlet;

import http.request.HttpServletRequest;

public interface HandlerMapping {

    Servlet getHandler(HttpServletRequest request);
}
