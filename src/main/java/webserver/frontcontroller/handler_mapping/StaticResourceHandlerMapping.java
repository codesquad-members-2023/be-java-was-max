package webserver.frontcontroller.handler_mapping;

import webserver.frontcontroller.controller.Handler;
import webserver.frontcontroller.controller.StaticResourceHandler;
import webserver.http.request.HttpRequest;
import webserver.util.FileUtils;

public class StaticResourceHandlerMapping implements HandlerMapping {

    @Override
    public Handler getHandler(HttpRequest request) {
        String path = request.getRequestLine().getRequestURI().getPath();
        if (FileUtils.getFileFromPath(path).isEmpty()) {
            return null;
        }
        return new StaticResourceHandler(path);
    }
}
