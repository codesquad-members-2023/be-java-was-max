package webserver;

import annotation.RequestMapping;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class HandlerMapping {

    public static final String STATIC = "/static";

    private HandlerMapping() {
    }

    public static MappingInfo map(HttpRequest httpRequest) throws InvocationTargetException,
            IllegalAccessException,
            IOException, NoSuchMethodException {
        MainController mainController = MainController.getInInstance();
        String url = httpRequest.getUrl();

        Method[] declaredMethods = mainController.getClass().getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                String value = annotation.value();
                Map<String, String> parameters = httpRequest.getParameters();
                if (value.equals(url) && parameters.size() == method.getParameterCount()) {
                    return new MappingInfo(url, method, mainController, parameters);
                }
            }
        }

        return new MappingInfo(STATIC + url);
    }
}
