package webserver.frontcontroller.adapter;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import webserver.frontcontroller.ModelAndView;
import webserver.frontcontroller.controller.Handler;
import webserver.frontcontroller.controller.StaticResourceHandler;

public class StaticResourceHandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof StaticResourceHandler;
    }

    @Override
    public ModelAndView handle(HttpRequest request, HttpResponse response, Object handler) {
        Handler controller = (Handler) handler;
        String viewName = controller.process(request, response);
        Map<String, Object> model = new HashMap<>();
        ModelAndView mv = new ModelAndView(viewName);
        mv.setModel(model);
        return mv;
    }
}
