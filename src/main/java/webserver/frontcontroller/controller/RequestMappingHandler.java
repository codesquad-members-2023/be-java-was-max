package webserver.frontcontroller.controller;

import webserver.annotation.RequestMapping;
import webserver.frontcontroller.Model;
import webserver.http.common.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class RequestMappingHandler implements Handler {

    private final Object controllerInstance;

    public RequestMappingHandler(Object controllerInstance) {
        this.controllerInstance = controllerInstance;
    }

    public boolean match(HttpRequest request) {
        return find(request).isPresent();
    }

    @Override
    public String process(HttpRequest request, HttpResponse response, Model model) {
        Optional<Method> optionalMethod = find(request);
        if (optionalMethod.isPresent()) {
            Method method = optionalMethod.get();
            try {
                return (String) method.invoke(controllerInstance, request, response, model);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private Optional<Method> find(HttpRequest request) {
        HttpMethod requestHttpMethod = request.getRequestLine().getHttpMethod();
        String requestPath = request.getRequestLine().getRequestURI().getPath();
        Method[] methods = controllerInstance.getClass().getMethods();
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
