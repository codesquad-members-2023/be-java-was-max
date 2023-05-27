package webserver.frontcontroller.adapter;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import webserver.frontcontroller.ModelAndView;
import webserver.frontcontroller.controller.Handler;
import webserver.frontcontroller.controller.RequestMappingHandler;

public class RequestMappingHandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof RequestMappingHandler;
    }

    @Override
    public ModelAndView handle(HttpRequest request, HttpResponse response, Object handler) {
        Handler controller = (Handler) handler;
        Map<String, Object> model = new HashMap<>();
        String viewName = controller.process(request, response);
        ModelAndView mv = new ModelAndView(viewName);

        if (viewName.startsWith("redirect:")) {
            viewName = viewName.replace("redirect:", "");
            mv.setViewName(viewName);
            model.put("redirect", Boolean.TRUE);
        }
        mv.setModel(model);
        return mv;
    }
}
