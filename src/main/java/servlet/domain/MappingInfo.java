package servlet.domain;

import controller.MainController;

import java.lang.reflect.Method;
import java.util.Map;

public class MappingInfo {
    private static final String STATIC = "/static";
    private static final String SRC_MAIN_RESOURCES = "src/main/resources";
    private final String url;
    private Method method;
    private Object object;
    private Map<String, String> params;


    public MappingInfo(String url) {
        this.url = url;
    }


    public MappingInfo(String url, Method method, Object object, Map<String, String> params) {
        this.url = url;
        this.method = method;
        this.object = object;
        this.params = params;
    }

    public MappingInfo(String url, Method method, Object object) {
        this.url = url;
        this.method = method;
        this.object = object;
    }

    public static MappingInfo staticOf(String path) {
        return new MappingInfo(STATIC + path);
    }

    public static MappingInfo of(Method method, HttpRequest httpRequest, MainController mainController) {
        if (httpRequest.hasParameters()) {
            return new MappingInfo(SRC_MAIN_RESOURCES + httpRequest.getUrl(), method, mainController, httpRequest.getParameters());
        }
        return new MappingInfo(SRC_MAIN_RESOURCES + httpRequest.getUrl(), method, mainController);
    }

    public String getUrl() {
        return url;
    }

    public Method getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public boolean isStatic() {
        return url.startsWith(STATIC);
    }

    public boolean hasPrams() {
        return params != null;
    }
}
