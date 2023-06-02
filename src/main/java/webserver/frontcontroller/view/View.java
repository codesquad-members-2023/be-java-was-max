package webserver.frontcontroller.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.frontcontroller.ModelAndView;
import webserver.frontcontroller.RequestDispatcher;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class View {

    private static final Logger logger = LoggerFactory.getLogger(View.class);

    private String viewPath;

    public View(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(ModelAndView modelAndView, HttpRequest request, HttpResponse response) {
        logger.debug("viewPath : {}", viewPath);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        boolean redirect = Boolean.parseBoolean(String.valueOf(modelAndView.getModel().getAttribute("redirect")));
        if (redirect) {
            dispatcher.redirect(response);
            return;
        }
        dispatcher.forward(response, modelAndView);
    }
}
