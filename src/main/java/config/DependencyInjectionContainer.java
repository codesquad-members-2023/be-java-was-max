package config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DependencyInjectionContainer {

    private Map<Class<?>, Object> beanMap;

    public DependencyInjectionContainer(CafeAppConfig cafeAppConfig)
        throws InvocationTargetException, IllegalAccessException {
        beanMap = new HashMap<>();
        initAppConfig(cafeAppConfig);
    }

    public Object get(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    private void initAppConfig(CafeAppConfig cafeAppConfig) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = cafeAppConfig.getClass()
            .getDeclaredMethods();
        for (Method method : methods) {
            Class<?> returnType = method.getReturnType();
            Object instance = method.invoke(cafeAppConfig);
            beanMap.put(returnType, instance);
        }
    }

}
