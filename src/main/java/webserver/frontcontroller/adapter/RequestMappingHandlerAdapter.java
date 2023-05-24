package webserver.frontcontroller.adapter;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import webserver.frontcontroller.ModelAndView;
import webserver.frontcontroller.controller.RequestMappingController;

public class RequestMappingHandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof RequestMappingController;
    }

    @Override
    public ModelAndView handle(HttpRequest request, HttpResponse response, Object handler) {
        RequestMappingController controller = (RequestMappingController) handler;
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();
        String viewName = controller.process(request, response);
        ModelAndView mv = new ModelAndView(viewName);
        mv.setModel(model);
        return mv;
    }

    private Map<String, String> createParamMap(HttpRequest request) {
        return request.getQueryString().getParameter();
    }
}
