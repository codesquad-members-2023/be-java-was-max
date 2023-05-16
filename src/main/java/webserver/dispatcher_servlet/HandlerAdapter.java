package webserver.dispatcher_servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class HandlerAdapter {

    public String handle(HttpRequest request, HttpResponse response, Servlet servlet)
        throws Exception {
        return servlet.invoke(request, response);
    }
}
