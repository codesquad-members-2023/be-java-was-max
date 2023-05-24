package webserver.frontcontroller.old;

import http.request.HttpRequest;

public interface HandlerMapping {

    Handler getHandler(HttpRequest request);
}
