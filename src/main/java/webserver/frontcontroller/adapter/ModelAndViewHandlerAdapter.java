package webserver.frontcontroller.adapter;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Map;
import webserver.frontcontroller.ModelAndView;
import webserver.frontcontroller.controller.ModelAndViewController;

public class ModelAndViewHandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof ModelAndViewController;
    }

    @Override
    public ModelAndView handle(HttpRequest request, HttpResponse response, Object handler) {
        ModelAndViewController controller = (ModelAndViewController) handler;

        Map<String, String> paramMap = createParamMap(request);

        return controller.process(paramMap);
    }

    private Map<String, String> createParamMap(HttpRequest request) {
        return request.getQueryString().getParameter();
    }
}
