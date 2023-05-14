package webserver.dispatcher_servlet;

import http.request.HttpServletRequest;
import http.response.HttpServletResponse;

public class HandlerAdapter {

    public String handle(HttpServletRequest request, HttpServletResponse response, Servlet servlet)
        throws Exception {
        return servlet.invoke(request, response);
    }
}
