package webserver.jsp;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;

public interface JspServlet {
    void init();

    void service(HttpRequest request, HttpResponse response) throws IOException;

    void destroy();
}
