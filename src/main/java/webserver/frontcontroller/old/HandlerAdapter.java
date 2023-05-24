package webserver.frontcontroller.old;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.lang.reflect.InvocationTargetException;

public class HandlerAdapter {

    public String handle(HttpRequest request, HttpResponse response, Handler handler)
        throws InvocationTargetException, IllegalAccessException {
        return handler.process(request, response);
    }
}
