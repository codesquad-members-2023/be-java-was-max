package servlet;

import http.request.HttpMethod;
import http.request.HttpRequest;

public class HomeServlet {
    // 적절한 메서드를 찾는다.
    // 해당 메서드에서 return 되는 경로를 반환한다.
    public String findViewPath(HttpRequest httpRequest) {
        return "index.html";
    }
}
