package webserver.frontcontroller.view;

import webserver.frontcontroller.ModelAndView;
import webserver.frontcontroller.RequestDispatcher;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class View {

    private String viewPath;

    public View(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(ModelAndView modelAndView, HttpRequest request, HttpResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        boolean redirect = Boolean.parseBoolean(String.valueOf(modelAndView.getModel().getAttribute("redirect")));
        if (redirect) {
            dispatcher.redirect(response);
            return;
        }
        dispatcher.forward(response, modelAndView);
    }
}
