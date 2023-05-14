package webserver.dispatcher_servlet;

import http.request.HttpServletRequest;
import http.response.HttpServletResponse;

public interface HttpServlet {

    void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
