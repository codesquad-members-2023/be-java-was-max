package webserver.jsp;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.io.OutputStreamWriter;

import static http.common.header.EntityHeaderType.CONTENT_TYPE;
// IMPORT INSERT CODE HERE

public class JspServletTemplate implements JspServlet {

    // CLASS INSERT CODE HERE

    @Override
    public void init() {
        // INIT METHOD INSERT CODE HERE
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        response.addHeader(CONTENT_TYPE, "text/html;charset=utf-8");
        OutputStreamWriter out = response.getMessageBodyWriter();
        // SERVICE METHOD INSERT CODE HERE
    }

    @Override
    public void destroy() {
        // DESTROY METHOD INSERT CODE HERE
    }
}
