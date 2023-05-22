package servlet;

import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewResolver;

public class DispatcherServlet {
    // 1. 적합한 컨트롤러를 찾는다.
    // 2. 적합한 메서드를 찾는다.
    // exception : 맵핑되는 url이 없으면 에러 발생.
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private final ViewResolver viewResolver;
    private String mappingUri;

    public DispatcherServlet(){
        this.viewResolver = new ViewResolver();
    }

    // TODO: 최종 경로를 return 하지 말고, ViewResolver에서 처리하게 끔 수정하기.
    public String run(HttpRequest httpRequest){
        this.mappingUri = mappingUri(httpRequest);
        return viewResolver.run(mappingUri);
    }

    public String mappingUri(HttpRequest httpRequest){
        String path = httpRequest.getPath();  // 컨트롤러에 의해, 가야 할 경로가 바뀔 수도 있다.
        String viewPath = path;  // 컨트롤러에 의해, 가야 할 경로가 바뀔 수도 있다.
        if (path.equals("/index.html")) {
            viewPath = new HomeServlet().findViewPath(httpRequest);
        }
        if (path.equals("/user/form.html")) {
            viewPath = "/user/form.html";
        }
        if (path.contains("/user/create")){
            viewPath = new UserServlet().findViewPath(httpRequest);
        }
        logger.debug("viewPath: {}", viewPath);
        return viewPath;
    }

    public String getMappingUri(){
        return mappingUri;
    }
}
