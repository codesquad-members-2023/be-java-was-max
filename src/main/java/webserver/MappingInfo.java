package webserver;

import java.lang.reflect.Method;

public class MappingInfo {
    private final String url;
    private Method method;
    private Object object;

    public MappingInfo(String url) {
        this.url = url;
    }

    public MappingInfo(String url, Method method, Object object) {
        this.url = url;
        this.method = method;
        this.object = object;
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

}
