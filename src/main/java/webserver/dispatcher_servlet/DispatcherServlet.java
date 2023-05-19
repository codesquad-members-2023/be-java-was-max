package webserver.dispatcher_servlet;

import static http.common.HttpStatus.FOUND;
import static http.common.header.ResponseHeaderType.LOCATION;
import static http.common.version.HttpVersion.HTTP_1_1;

import config.CafeAppConfig;
import config.DependencyInjectionContainer;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.component.StatusLine;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet implements HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String PACKAGE_NAME = "cafe.app.user.controller";
    private static final String DEFAULT_VIEW_RESOLVER_PREFIX = "/";
    private static final String DEFAULT_VIEW_RESOLVER_POSTFIX = ".html";

    private final HandlerMapping handlerMapping;
    private final HandlerAdapter handlerAdapter;
    private final ViewResolver viewResolver;

    public DispatcherServlet() throws IOException, InvocationTargetException, IllegalAccessException {
        DependencyInjectionContainer container = new DependencyInjectionContainer(new CafeAppConfig());
        RequestMappingExplorer explorer = new RequestMappingExplorer(container);
        this.handlerMapping = explorer.scanRequestMapping(PACKAGE_NAME);
        this.handlerAdapter = new HandlerAdapter();
        this.viewResolver = new ViewResolver(DEFAULT_VIEW_RESOLVER_PREFIX, DEFAULT_VIEW_RESOLVER_POSTFIX);
    }

    @Override
    public void doDispatch(final HttpRequest request, final HttpResponse response) throws Exception {

        // 핸들러 가져오기
        Handler handler = handlerMapping.getHandler(request);

        // 핸들러 위임 실행
        String viewName = handlerAdapter.handle(request, response, handler);

        // "redirect:"로 시작하는 경우 리다이렉션 수행
        if (viewName.startsWith("redirect:")) {
            String location = viewResolver.getView(viewName.replace("redirect:", ""));
            processRedirection(location, response);
            return;
        }

        // 컨트롤러가 반환한 view에 따른 뷰 경로 변환
        // ex) view = user/login => viewPath = /user/login.html
        String viewPath = viewResolver.getView(viewName);

        // viewPath에 따른 파일 렌더링
        viewResolver.render(viewPath, request, response);
    }

    private void processRedirection(String location, final HttpResponse response) {
        response.addHeader(LOCATION, location);
        response.setStatusLine(new StatusLine(HTTP_1_1, FOUND));
        // TODO: 바디가 없어도 리다이렉션이 되도록 개선 필요
        response.setMessageBody(new byte[0]);
    }
}
