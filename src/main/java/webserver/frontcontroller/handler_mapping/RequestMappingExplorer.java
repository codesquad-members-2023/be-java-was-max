package webserver.frontcontroller.handler_mapping;

import cafe.config.CafeAppContainer;
import webserver.annotation.Controller;
import webserver.frontcontroller.controller.RequestMappingHandler;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import static webserver.util.PackageExplorer.scanClasses;

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
