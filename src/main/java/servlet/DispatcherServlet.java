package servlet;

import servlet.controller.*;
import webserver.util.HttpRequestUtils;
import webserver.util.HttpResponseUtils;
import webserver.util.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static webserver.util.HttpStatus.FOUND;

public class DispatcherServlet {
    private static final String REDIRECT_URL_PREFIX = "redirect:";
    private static final String COLON = ":";
    private static final int REDIRECT_URL_IDX = 1;
    private static final Map<String, Controller> controllerMap = addController();

    private static Map<String, Controller> addController() {
        HashMap<String, Controller> controllerMap = new HashMap<>();

        controllerMap.put("/", new IndexController());
        controllerMap.put("/index.html", new IndexController());
        controllerMap.put("/user/form", new UserFormController());
        controllerMap.put("/user/create", new UserSaveController());
        controllerMap.put("/user/login.html", new LoginFormController());
        controllerMap.put("/user/login", new LoginController());

        return controllerMap;
    }

    public static String service(HttpRequestUtils httpRequest, HttpResponseUtils httpResponse) {
        String requestUrl = httpRequest.getUrl();

        Controller controller = controllerMap.get(requestUrl);
        // CSS, JS 와 같은 static 타입 요청이 올 경우
        if (controller == null) {
            return requestUrl;
        }

        String viewName = controller.process(httpRequest.getParameters());

        // redirect 요청일 경우
        if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
            String redirectUrl = viewName.split(COLON)[REDIRECT_URL_IDX];
            httpResponse.setStatusCode(FOUND);
            httpResponse.setRedirectUrl(redirectUrl);

            return REDIRECT_URL_PREFIX;
        }

        // 뷰를 반환하는 정상 요청인 경우
        return viewName;
    }
}

