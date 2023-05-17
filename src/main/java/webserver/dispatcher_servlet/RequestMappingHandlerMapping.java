package webserver.dispatcher_servlet;

import http.request.HttpRequest;
import java.util.Set;

public class RequestMappingHandlerMapping implements HandlerMapping {

    private final Set<Handler> handlers;

    public RequestMappingHandlerMapping(Set<Handler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public Handler getHandler(HttpRequest request) {
        return handlers.stream()
            .filter(handler -> handler.match(request))
            .findAny()
            .orElse(null);
    }
}
