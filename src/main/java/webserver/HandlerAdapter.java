package webserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerAdapter {

    private HandlerAdapter() {
    }

    public static String process(MappingInfo mappingInfo)
            throws InvocationTargetException, IllegalAccessException {
        Method method = mappingInfo.getMethod();
        Object object = mappingInfo.getObject();
        if (mappingInfo.getUrl().startsWith("/static")) {
            return mappingInfo.getUrl();
        }
        return (String) method.invoke(object);
    }
}
