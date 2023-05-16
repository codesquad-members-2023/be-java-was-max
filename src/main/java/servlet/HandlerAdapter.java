package servlet;

import servlet.domain.MappingInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class HandlerAdapter {

    public static final String TEMPLATES = "/templates";

    private HandlerAdapter() {
    }

    public static String process(MappingInfo mappingInfo)
            throws InvocationTargetException, IllegalAccessException {
        Method method = mappingInfo.getMethod();
        Object object = mappingInfo.getObject();

        if (mappingInfo.isStatic()) {
            return mappingInfo.getUrl();
        }

        Map<String, String> params = mappingInfo.getParams();
        if (params.isEmpty()) {
            return getResult((String) method.invoke(object));
        } else {
            return getResult((String) method.invoke(object, params.values().toArray(new Object[0])));
        }
    }

    private static String getResult(String result) {
        return result.startsWith("/") ? TEMPLATES + result : result;
    }
}
