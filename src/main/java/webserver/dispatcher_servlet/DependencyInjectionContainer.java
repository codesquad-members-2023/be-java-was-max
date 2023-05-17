package webserver.dispatcher_servlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DependencyInjectionContainer {

    private Map<Class<?>, Object> beanMap;

    public DependencyInjectionContainer(UserAppConfig userAppConfig)
        throws InvocationTargetException, IllegalAccessException {
        beanMap = new HashMap<>();
        initAppConfig(userAppConfig);
    }

    public Object get(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    private void initAppConfig(UserAppConfig userAppConfig) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = userAppConfig.getClass()
            .getDeclaredMethods();
        for (Method method : methods) {
            Class<?> returnType = method.getReturnType();
            Object instance = method.invoke(userAppConfig);
            beanMap.put(returnType, instance);
        }
    }

}
