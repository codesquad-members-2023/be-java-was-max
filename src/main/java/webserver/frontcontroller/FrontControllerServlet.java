package webserver.frontcontroller;

import config.CafeAppConfig;
import config.DependencyInjectionContainer;
import http.common.HttpStatus;
import http.common.version.HttpVersion;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.component.StatusLine;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import webserver.frontcontroller.adapter.ModelAndViewHandlerAdapter;
import webserver.frontcontroller.adapter.MyHandlerAdapter;
import webserver.frontcontroller.adapter.ViewNameHandlerAdapter;
import webserver.frontcontroller.old.HandlerMapping;
import webserver.frontcontroller.old.RequestMappingExplorer;

public class FrontControllerServlet {

    private static final String PACKAGE_NAME = "cafe.app.user.controller";
    private static final String DEFAULT_VIEW_RESOLVER_PREFIX = "/";
    private static final String DEFAULT_VIEW_RESOLVER_POSTFIX = ".html";
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();
    private HandlerMapping handlerMapping;

    public FrontControllerServlet() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        try {
            DependencyInjectionContainer container = new DependencyInjectionContainer(new CafeAppConfig());
            RequestMappingExplorer explorer = new RequestMappingExplorer(container);
            this.handlerMapping = explorer.scanRequestMapping(PACKAGE_NAME);
        } catch (InvocationTargetException | IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ViewNameHandlerAdapter());
        handlerAdapters.add(new ModelAndViewHandlerAdapter());
    }

    public void service(HttpRequest request, HttpResponse response) {
        Object handler = handlerMapping.getHandler(request);

        if (handler == null) {
            response.setStatusLine(new StatusLine(new HttpVersion(1, 1), HttpStatus.FOUND));
            return;
        }

        MyHandlerAdapter handlerAdapter = getHandlerAdapter(handler);

        ModelAndView mv = handlerAdapter.handle(request, response, handler);

        View view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
            .filter(ha -> ha.supports(handler))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler));
    }

    private View viewResolver(String viewName) {
        return new View(DEFAULT_VIEW_RESOLVER_PREFIX + viewName + DEFAULT_VIEW_RESOLVER_POSTFIX);
    }
}
