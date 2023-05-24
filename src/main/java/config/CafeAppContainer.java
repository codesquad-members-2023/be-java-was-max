package config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CafeAppContainer {

    private final Map<Class<?>, Object> beanMap;

    public CafeAppContainer(CafeAppConfig cafeAppConfig)
        throws InvocationTargetException, IllegalAccessException {
        beanMap = new HashMap<>();
        initAppConfig(cafeAppConfig);
    }

    public Object get(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    private void initAppConfig(CafeAppConfig cafeAppConfig) {
        Method[] methods = cafeAppConfig.getClass().getDeclaredMethods();
        try {
            for (Method method : methods) {
                Class<?> returnType = method.getReturnType();
                Object instance = method.invoke(cafeAppConfig);
                beanMap.put(returnType, instance);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
