package servlet;

import servlet.controller.Controller;
import servlet.controller.IndexController;
import servlet.controller.UserFormController;
import servlet.controller.UserSaveController;
import webserver.util.HttpRequestUtils;
import webserver.util.HttpResponseUtils;

import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet {

    private static final int OK = 200;
    private static final int FOUND = 302;
    private static final int REDIRECT_URL_IDX = 1;
    private static final Map<String, Controller> controllerMap = addController();

    private static Map<String, Controller> addController() {
        HashMap<String, Controller> controllerMap = new HashMap<>();

        controllerMap.put("/", new IndexController());
        controllerMap.put("/user/form", new UserFormController());
        controllerMap.put("/user/create", new UserSaveController());

        return controllerMap;
    }

    public static String service(HttpRequestUtils httpRequest, HttpResponseUtils httpResponse) {
        String requestUrl = httpRequest.getUrl();

        Controller controller = controllerMap.get(requestUrl);
        // CSS, JS 와 같은 static 타입 요청이 올 경우
        if (controller == null) {
            httpResponse.setStatusCode(OK);
            return requestUrl;
        }

        String viewName = controller.process(httpRequest.getParameters());

        // redirect 요청일 경우
        if (viewName.startsWith("redirect:")) {
            httpResponse.setStatusCode(FOUND);
            return viewName.split(":")[REDIRECT_URL_IDX];
        }

        httpResponse.setStatusCode(200);
        // 뷰를 반환하는 정상 요청인 경우
        return viewName;
    }
}

