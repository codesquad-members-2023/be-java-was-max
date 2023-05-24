package webserver.frontcontroller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Map;

public class View {

    private String viewPath;

    public View(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) {
        modelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpRequest request) {
        model.forEach(request::setAttribute);
    }
}
