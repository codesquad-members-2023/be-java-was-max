package webserver.dispatcher_servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface HttpServlet {

    void doDispatch(HttpRequest request, HttpResponse response) throws Exception;
}
