package webserver.dispatcher_servlet;

import cafe.app.user.repository.MemoryUserRepository;
import cafe.app.user.service.UserService;
import cafe.app.user.validator.UserValidator;
import http.common.HttpMethod;
import http.request.HttpServletRequest;
import http.response.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Servlet {

    private HttpMethod httpMethod;
    private Method method;

    public Servlet(HttpMethod httpMethod, Method method) {
        this.httpMethod = httpMethod;
        this.method = method;
        this.method.setAccessible(true);
    }

    public String invoke(HttpServletRequest request, HttpServletResponse response)
        throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Object instance = method.getDeclaringClass().getDeclaredConstructor(UserService.class)
            .newInstance(new UserService(new MemoryUserRepository(), new UserValidator()));
        return (String) method.invoke(instance, request, response);
    }

    @Override
    public String toString() {
        return "Servlet{" +
            "requestMethod=" + httpMethod +
            ", method=" + method +
            '}';
    }


}
