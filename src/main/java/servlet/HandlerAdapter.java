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

        String url = mappingInfo.getUrl();
        if (mappingInfo.isStatic()) {
            return url;
        }

        if (url.startsWith("/posts/") && !url.equals("/posts/form")) {
            int index = url.lastIndexOf("/");
            String id = url.substring(index+1);
            return getResult((String) method.invoke(object, Integer.valueOf(id)));
        }

        if (mappingInfo.hasPrams()) {
            Map<String, String> params = mappingInfo.getParams();
            return getResult((String) method.invoke(object, params.values().toArray(new Object[0])));
        } else {
            return getResult((String) method.invoke(object));
        }
    }

    private static String getResult(String result) {
        return result.startsWith("/") ? TEMPLATES + result : result;
    }
}
