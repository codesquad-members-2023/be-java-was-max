package servlet;

import annotation.RequestMapping;
import controller.MainController;
import servlet.domain.HttpRequest;
import servlet.domain.MappingInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class HandlerMapping {

    private HandlerMapping() {
    }

    public static MappingInfo map(HttpRequest httpRequest) throws InvocationTargetException,
            IllegalAccessException,
            IOException, NoSuchMethodException {
        MainController mainController = MainController.getInInstance();

        Method[] declaredMethods = mainController.getClass().getDeclaredMethods();

        for (Method method : declaredMethods) {
            Optional<MappingInfo> mappingInfo = getMappingInfo(httpRequest, mainController, method);
            if (mappingInfo.isPresent()) {
                return mappingInfo.get();
            }
        }

        return MappingInfo.staticOf(httpRequest.getPath());
    }

    private static Optional<MappingInfo> getMappingInfo(HttpRequest httpRequest,
                                                        MainController mainController,
                                                        Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            String path = annotation.value();
            if (httpRequest.isMatching(method, path)) {
                return Optional.of(MappingInfo.of(method, httpRequest, mainController));
            }
        }
        return Optional.empty();
    }

}
