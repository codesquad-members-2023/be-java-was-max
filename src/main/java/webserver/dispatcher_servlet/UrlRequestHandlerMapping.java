package webserver.dispatcher_servlet;

import http.request.HttpRequest;
import java.util.Map;

public class UrlRequestHandlerMapping implements HandlerMapping {

    private Map<String, Servlet> mappingMap;

    public UrlRequestHandlerMapping(Map<String, Servlet> mappingMap) {
        this.mappingMap = mappingMap;
    }

    @Override
    public Servlet getHandler(HttpRequest request) {
        for (String key : mappingMap.keySet()) {
            Servlet servlet = mappingMap.get(key);
            if (request.getPath()
                .equals(key) && servlet.getHttpMethod()
                .equals(request.getHttpMethod())) {
                return mappingMap.get(key);
            }
        }
        return null;
    }
}
