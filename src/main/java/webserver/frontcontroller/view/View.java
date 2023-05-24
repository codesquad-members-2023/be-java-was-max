package webserver.frontcontroller.view;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Map;
import webserver.frontcontroller.RequestDispatcher;

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
            dispatcher.redirect(response);
            return;
        }
        dispatcher.forward(response);
    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpRequest request) {
        model.forEach(request::setAttribute);
    }
}
