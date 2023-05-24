package webserver.frontcontroller.old;

import annotation.RequestMapping;
import http.common.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import webserver.frontcontroller.controller.RequestMappingController;

public class Handler implements RequestMappingController {

    private final Object controllerInstance;

    public Handler(Object controllerInstance) {
        this.controllerInstance = controllerInstance;
    }

    public boolean match(HttpRequest request) {
        return find(request).isPresent();
    }

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        Optional<Method> optionalMethod = find(request);
        if (optionalMethod.isPresent()) {
            Method method = optionalMethod.get();
            try {
                return (String) method.invoke(controllerInstance, request, response);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private Optional<Method> find(HttpRequest request) {
        HttpMethod requestHttpMethod = request.getHttpMethod();
        String requestPath = request.getPath();
        Method[] methods = controllerInstance.getClass()
            .getMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }
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
