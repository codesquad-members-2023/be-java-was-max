package webserver;

import annotation.RequestMapping;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerMapping {

    private HandlerMapping() {
    }

    public static MappingInfo map(HttpRequest httpRequest) throws InvocationTargetException,
            IllegalAccessException,
            IOException, NoSuchMethodException {
        MainController mainController = MainController.getInInstance();
        String url = httpRequest.getUrl();

        if (url.startsWith("/static")) {
            return new MappingInfo(url);
        }

        Method[] declaredMethods = mainController.getClass().getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                String value = annotation.value();
                if (value.equals(url)) {
                    return new MappingInfo(url, method, mainController);
                }
            }
        }
        throw new NoSuchMethodException();
    }
}
