package webserver.frontcontroller.adapter;

import webserver.frontcontroller.Model;
import webserver.frontcontroller.ModelAndView;
import webserver.frontcontroller.controller.Handler;
import webserver.frontcontroller.controller.RequestMappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.session.SessionContainer;

public class RequestMappingHandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof RequestMappingHandler;
    }

    @Override
    public ModelAndView handle(HttpRequest request, HttpResponse response, Object handler) {
        Handler controller = (Handler) handler;
        Model model = new Model();
        String viewName = controller.process(request, response, model);
        HttpSession session = SessionContainer.getSession(request.getSessionId());
        // 세션에 있는 데이터들을 model 객체로 복사
        sessionToModel(session, model);

        if (viewName.startsWith("redirect:")) {
            viewName = viewName.replace("redirect:", "");
            model.addAttribute("redirect", Boolean.TRUE);
        }
        return new ModelAndView(viewName, model);
    }

    private void sessionToModel(HttpSession session, Model model) {
        session.keySet().forEach(key -> model.addAttribute(key, session.getAttribute(key)));
    }
}
