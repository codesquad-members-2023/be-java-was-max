package webserver.dispatcher_servlet;

import static util.PackageExplorer.findAllClassesUsingGoogleGuice;

import annotation.Controller;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestMappingExplorer {

    private final DependencyInjectionContainer container;

    public RequestMappingExplorer(DependencyInjectionContainer container) {
        this.container = container;
    }

    public HandlerMapping scanRequestMapping(String packageName) throws IOException {
        Set<Handler> handlers = findAllClassesUsingGoogleGuice(packageName).stream()
            .filter(aClass -> aClass.isAnnotationPresent(Controller.class))
            .map(aClass -> new Handler(container.get(aClass)))
            .collect(Collectors.toSet());
        return new RequestMappingHandlerMapping(handlers);
    }
}
