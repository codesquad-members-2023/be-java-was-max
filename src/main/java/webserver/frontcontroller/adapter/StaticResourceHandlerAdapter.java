package webserver.frontcontroller.adapter;

import webserver.frontcontroller.Model;
import webserver.frontcontroller.ModelAndView;
import webserver.frontcontroller.controller.Handler;
import webserver.frontcontroller.controller.StaticResourceHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class StaticResourceHandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof StaticResourceHandler;
    }

    @Override
    public ModelAndView handle(HttpRequest request, HttpResponse response, Object handler) {
        Handler controller = (Handler) handler;
        Model model = new Model();
        String viewName = controller.process(request, response, model);
        return new ModelAndView(viewName, model);
    }
}
