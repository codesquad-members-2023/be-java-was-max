package webserver.frontcontroller;

import config.CafeAppConfig;
import config.CafeAppContainer;
import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.component.StatusLine;
import webserver.frontcontroller.adapter.MyHandlerAdapter;
import webserver.frontcontroller.adapter.RequestMappingHandlerAdapter;
import webserver.frontcontroller.adapter.StaticResourceHandlerAdapter;
import webserver.frontcontroller.controller.Handler;
import webserver.frontcontroller.handler_mapping.HandlerMapping;
import webserver.frontcontroller.handler_mapping.RequestMappingExplorer;
import webserver.frontcontroller.handler_mapping.StaticResourceHandlerMapping;
import webserver.frontcontroller.view.View;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static http.common.version.HttpVersion.HTTP_1_1;
import static util.FileUtils.getFileFromPath;

public class FrontControllerServlet {

    private static final String BASE_PACKAGE = "cafe";
    private static final String DEFAULT_VIEW_RESOLVER_PREFIX = "/";
    private static final String DEFAULT_VIEW_RESOLVER_POSTFIX = ".html";
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();
    private List<HandlerMapping> handlerMappings;

    public FrontControllerServlet() {
        initHandlerMappings();
        initHandlerAdapters();
    }

    private void initHandlerMappings() {
        handlerMappings = new ArrayList<>();
        handlerMappings.add(getRequestMappingHandlerMapping());
        handlerMappings.add(new StaticResourceHandlerMapping());
    }

    private HandlerMapping getRequestMappingHandlerMapping() {
        try {
            CafeAppContainer container = new CafeAppContainer(new CafeAppConfig());
            RequestMappingExplorer explorer = new RequestMappingExplorer(container);
            return explorer.scanRequestMapping(BASE_PACKAGE);
        } catch (InvocationTargetException | IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new RequestMappingHandlerAdapter());
        handlerAdapters.add(new StaticResourceHandlerAdapter());
    }

    public void service(HttpRequest request, HttpResponse response) {
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatusLine(new StatusLine(HTTP_1_1, HttpStatus.NOT_FOUND));
            return;
        }

        MyHandlerAdapter handlerAdapter = getHandlerAdapter(handler);

        ModelAndView mv = handlerAdapter.handle(request, response, handler);

        View view = viewResolver(mv);
        view.render(mv, request, response);
    }

    private Handler getHandler(HttpRequest request) {
        for (HandlerMapping hm : handlerMappings) {
            Handler handler = hm.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(ha -> ha.supports(handler))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler));
    }

    private View viewResolver(ModelAndView mv) {
        String viewName = mv.getViewName();
        boolean redirect = Boolean.parseBoolean(String.valueOf(mv.getModel().getAttribute("redirect")));
        if (getFileFromPath(viewName).isPresent()) {
            return new View(viewName);
        }
        if (redirect) {
            return new View(viewName);
        }
        return new View(DEFAULT_VIEW_RESOLVER_PREFIX + viewName + DEFAULT_VIEW_RESOLVER_POSTFIX);
    }
}
