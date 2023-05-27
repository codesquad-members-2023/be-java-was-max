package webserver.frontcontroller.handler_mapping;

import static util.PackageExplorer.scanClasses;

import annotation.Controller;
import config.CafeAppContainer;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import webserver.frontcontroller.controller.RequestMappingHandler;

public class RequestMappingExplorer {

    private final CafeAppContainer container;

    public RequestMappingExplorer(CafeAppContainer container) {
        this.container = container;
    }

    public HandlerMapping scanRequestMapping(String packageName) throws IOException {
        Set<RequestMappingHandler> handlers = scanClasses(packageName).stream()
            .filter(aClass -> aClass.isAnnotationPresent(Controller.class))
            .map(aClass -> new RequestMappingHandler(container.get(aClass)))
            .collect(Collectors.toSet());
        return new RequestMappingHandlerMapping(handlers);
    }
}
