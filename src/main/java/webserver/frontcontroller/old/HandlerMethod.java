package webserver.frontcontroller.old;

import http.common.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.lang.reflect.Method;

public class HandlerMethod {

    private HttpMethod httpMethod;
    private String path;
    private Method method;

    public HandlerMethod(HttpMethod httpMethod, String path, Method method) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.method = method;
    }

    public boolean match(HttpMethod httpMethod, String path) {
        return this.httpMethod.equals(httpMethod) && this.path.equals(path);
    }

    public void service(HttpRequest request, HttpResponse response) {

    }
}
