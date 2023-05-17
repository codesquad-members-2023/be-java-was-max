package webserver.dispatcher_servlet;

import annotation.RequestMapping;
import http.common.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class Handler {

    private final Object controllerInstance;

    public Handler(Object controllerInstance) {
        this.controllerInstance = controllerInstance;
    }

    public boolean match(HttpRequest request) {
        return find(request).isPresent();
    }

    public String service(HttpRequest request, HttpResponse response)
        throws InvocationTargetException, IllegalAccessException {
        Optional<Method> optionalMethod = find(request);
        if (optionalMethod.isPresent()) {
            Method method = optionalMethod.get();
            return (String) method.invoke(controllerInstance, request, response);
        }
        return null;
    }

    private Optional<Method> find(HttpRequest request) {
        HttpMethod requestHttpMethod = request.getHttpMethod();
        String requestPath = request.getPath();
        Method[] methods = controllerInstance.getClass()
            .getMethods();
        for (Method method : methods) {
            RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
            HttpMethod httpMethod = requestMapping.method();
            String path = requestMapping.path();
            if (requestHttpMethod.equals(httpMethod) && requestPath.equals(path)) {
                return Optional.of(method);
            }
        }
        return Optional.empty();
    }
}
