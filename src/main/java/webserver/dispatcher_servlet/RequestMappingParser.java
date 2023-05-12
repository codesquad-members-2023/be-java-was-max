package webserver.dispatcher_servlet;

import static java.util.stream.Collectors.toMap;
import static util.PackageExplorer.findAllClassesUsingGoogleGuice;

import annotation.Controller;
import annotation.RequestMapping;
import http.common.HttpMethod;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public final class RequestMappingParser {

    private RequestMappingParser() {

    }

    public static Map<String, Servlet> scanRequestMapping(String packageName)
        throws IOException {
        return findAllClassesUsingGoogleGuice(packageName).stream()
            .filter(aClass -> aClass.isAnnotationPresent(Controller.class))
            .flatMap(aClass -> Arrays.stream(aClass.getDeclaredMethods()))
            .filter(method -> method.isAnnotationPresent(RequestMapping.class))
            .collect(toMap(
                method -> method.getDeclaredAnnotation(RequestMapping.class).path(),
                method -> {
                    HttpMethod httpMethod = method.getDeclaredAnnotation(RequestMapping.class)
                        .method();
                    return new Servlet(httpMethod, method);
                }
            ));
    }
}
