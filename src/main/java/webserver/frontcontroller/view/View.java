package webserver.frontcontroller.view;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.frontcontroller.RequestDispatcher;

import java.util.Map;

public class View {

    private String viewPath;

    public View(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) {
        modelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        boolean redirect = Boolean.parseBoolean(String.valueOf(model.get("redirect")));
        if (redirect) {
            dispatcher.redirect(request, response);
            return;
        }
        dispatcher.forward(request, response);
    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpRequest request) {
        model.forEach(request::setAttribute);
    }
}
