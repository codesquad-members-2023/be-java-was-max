package webserver.frontcontroller.handler_mapping;

import http.request.HttpRequest;
import java.util.Set;
import webserver.frontcontroller.controller.RequestMappingHandler;

public class RequestMappingHandlerMapping implements HandlerMapping {

    private final Set<RequestMappingHandler> handlers;

    public RequestMappingHandlerMapping(Set<RequestMappingHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public RequestMappingHandler getHandler(HttpRequest request) {
        return handlers.stream()
            .filter(handler -> handler.match(request))
            .findAny()
            .orElse(null);
    }
}
