package servlet;

import annotation.RequestMapping;
import servlet.domain.HttpRequest;
import servlet.domain.MappingInfo;
import controller.MainController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

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
            Optional<MappingInfo> mappingInfo = getMappingInfo(httpRequest, mainController, url, method);
            if (mappingInfo.isPresent()) {
                return mappingInfo.get();
            }
        }

        return new MappingInfo(STATIC + url);
    }

    private static Optional<MappingInfo> getMappingInfo(HttpRequest httpRequest,
                                                        MainController mainController,
                                                        String url,
                                                        Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            String value = annotation.value();
            Map<String, String> parameters = httpRequest.getParameters();
            if (isEqual(url, method, value, parameters)) {
                return Optional.of(new MappingInfo(url, method, mainController, parameters));
            }
        }
        return Optional.empty();
    }

    private static boolean isEqual(String url, Method method, String value, Map<String, String> parameters) {
        return value.equals(url) && (parameters.size() == method.getParameterCount());
    }
}
