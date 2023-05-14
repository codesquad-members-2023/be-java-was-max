package webserver.dispatcher_servlet;

import http.request.HttpServletRequest;
import java.util.Map;

public class UrlRequestHandlerMapping implements HandlerMapping {

    private Map<String, Servlet> mappingMap;

    public UrlRequestHandlerMapping(Map<String, Servlet> mappingMap) {
        this.mappingMap = mappingMap;
    }

    @Override
    public Servlet getHandler(HttpServletRequest request) {
        for (String key : mappingMap.keySet()) {
            if (request.getPath().equals(key)) {
                return mappingMap.get(key);
            }
        }
        return null;
    }
}
