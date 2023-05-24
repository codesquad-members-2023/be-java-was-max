package controller;

import com.google.common.collect.Maps;
import http.HttpUtil;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.StatusLine;
import view.ViewResolver;

import java.io.IOException;
import java.util.Map;

public class FrontController {
    public static final String REDIRECT_URL_PREFIX = "redirect:";
    public static final String USER_SAVE_URL = "/user/create";
    public static final String USER_LOGIN_URL = "/user/login";
    public static final String ARTICLE_LIST_URL = "/";

    public static final String PROTOCOL = "HTTP/1.1";
    public static final String HTTP_STATUS_200 = "200";
    public static final String REASON_PHRASE_200 = "OK";
    public static final String HTTP_STATUS_302 = "302";
    public static final String REASON_PHRASE_302 = "FOUND";

    private final ViewResolver viewResolver = new ViewResolver();
    private final Map<String, Controller> controllers = Maps.newHashMap(); // <요청 URL, 처리 대상>

    public FrontController() {
        controllers.put(USER_SAVE_URL, new UserSaveController());
        controllers.put(USER_LOGIN_URL, new UserLoginController());
        controllers.put(ARTICLE_LIST_URL, new ArticleListController());
    }

    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String url = request.getRequestLine().getUrl();

        Controller controller = controllers.get(url);

        if (controller == null) {
            viewResolver.resolve(url, response);
            response.setStatusLine(new StatusLine(PROTOCOL, HTTP_STATUS_200, REASON_PHRASE_200));
            return;
        }

        String viewName = controller.process(request, response);

        if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
            response.setStatusLine(new StatusLine(PROTOCOL, HTTP_STATUS_302, REASON_PHRASE_302));
            response.getHeaders().addLocation(viewName.replace(REDIRECT_URL_PREFIX, ""));
            return;
        }

        viewResolver.resolve(viewName, response);
        response.setStatusLine(new StatusLine(PROTOCOL, HTTP_STATUS_200, REASON_PHRASE_200));
        response.getHeaders().addContentType(HttpUtil.findContentTypeValue(viewName));
    }
}
