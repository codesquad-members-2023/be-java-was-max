package container.domain;

import java.lang.reflect.Method;
import java.util.Map;

public class MappingInfo {
    public static final String STATIC = "/static";
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
}
