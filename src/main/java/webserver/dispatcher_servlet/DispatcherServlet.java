package webserver.dispatcher_servlet;

import static http.common.HttpStatus.FOUND;
import static http.common.HttpVersion.HTTP_1_1;
import static webserver.dispatcher_servlet.RequestMappingParser.scanRequestMapping;

import http.request.HttpServletRequest;
import http.response.HttpServletResponse;
import http.response.StatusLine;
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
    public void doDispatch(final HttpServletRequest request, final HttpServletResponse response)
        throws Exception {

        // 핸들러 가져오기
        Servlet mappedHandler = getHandler(request);

        // 핸들러 위임 실행
        String view = handlerAdapter.handle(request, response, mappedHandler);

        // 리다이렉션인 경우 리다이렉션 수행
        if (view.startsWith("redirect:")) {
            String location = viewResolver.getView(view.replace("redirect:", ""));
            processRedirection(location, response);
            return;
        }

        // 컨트롤러가 반환한 view에 따른 뷰 경로 변환
        // ex) view = user/login => viewPath = /user/login.html
        String viewPath = viewResolver.getView(view);

        // viewPath에 따른 파일 렌더링
        viewResolver.render(viewPath, response);
    }

    private Servlet getHandler(HttpServletRequest request) {
        return handlerMapping.getHandler(request);
    }

    private void processRedirection(String location, final HttpServletResponse response) {
        response.addHeader("location", location);
        response.setStatusLine(new StatusLine(HTTP_1_1, FOUND));
    }
}
