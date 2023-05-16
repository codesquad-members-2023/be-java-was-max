package webserver.dispatcher_servlet;

import static http.common.HttpStatus.FOUND;
import static http.common.header.ResponseHeaderType.LOCATION;
import static http.common.version.HttpVersion.HTTP_1_1;
import static webserver.dispatcher_servlet.RequestMappingParser.scanRequestMapping;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.component.StatusLine;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet implements HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;
    private ViewResolver viewResolver;

    public DispatcherServlet() throws IOException {
        Map<String, Servlet> mappingMap = scanRequestMapping("cafe.app.user.controller");
        this.handlerMapping = new UrlRequestHandlerMapping(mappingMap);
        handlerAdapter = new HandlerAdapter();
        viewResolver = new ViewResolver("/", ".html");
    }

    @Override
    public void doDispatch(final HttpRequest request, final HttpResponse response)
        throws Exception {

        // 핸들러 가져오기
        Servlet mappedHandler = getHandler(request);
        logger.debug("mappedHandler : {}", mappedHandler);

        // 핸들러 위임 실행
        String viewName = handlerAdapter.handle(request, response, mappedHandler);

        // 리다이렉션인 경우 리다이렉션 수행
        if (viewName.startsWith("redirect:")) {
            String location = viewResolver.getView(viewName.replace("redirect:", ""));
            processRedirection(location, response);
            return;
        }

        // 컨트롤러가 반환한 view에 따른 뷰 경로 변환
        // ex) view = user/login => viewPath = /user/login.html
        String viewPath = viewResolver.getView(viewName);

        // viewPath에 따른 파일 렌더링
        viewResolver.render(viewPath, response);
    }

    private Servlet getHandler(HttpRequest request) {
        return handlerMapping.getHandler(request);
    }

    private void processRedirection(String location, final HttpResponse response) {
        response.addHeader(LOCATION, location);
        response.setStatusLine(new StatusLine(HTTP_1_1, FOUND));
        // TODO: 바디가 없어도 리다이렉션이 되도록 개선 필요
        response.setMessageBody(new byte[0]);
    }
}
