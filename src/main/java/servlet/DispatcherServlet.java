package servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ViewResolver;

public class DispatcherServlet {
    // 1. 적합한 컨트롤러를 찾는다.
    // 2. 적합한 메서드를 찾는다.
    private final ViewResolver viewResolver;

    public DispatcherServlet(){
        this.viewResolver = new ViewResolver();
    }

    public void run(HttpRequest httpRequest, HttpResponse httpResponse){
        viewResolver.run(mappingUri(httpRequest, httpResponse), httpResponse);
    }

    public String mappingUri(HttpRequest httpRequest, HttpResponse httpResponse){
        String path = httpRequest.getPath();  // 컨트롤러에 의해, 가야 할 경로가 바뀔 수도 있다.
        String viewPath = path;  // 컨트롤러에 의해, 가야 할 경로가 바뀔 수도 있다.
        if (path.equals("/index.html")) {
            viewPath = new HomeServlet().findViewPath(httpRequest);
        }
        if (path.equals("/user/form.html")) {
            viewPath = "/user/form.html";
        }
        if (path.contains("/user/create")){
            viewPath = new UserCreateServlet().findViewPath(httpRequest);
        }
        if (path.contains("/user/login")) {
            viewPath = new UserLoginServlet().findViewPath(httpRequest, httpResponse);
        }
        return viewPath;
    }
}
