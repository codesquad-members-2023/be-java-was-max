package webserver.frontcontroller;

import webserver.http.common.HttpStatus;

public class ModelAndView {

    private String viewName;
    private Model model;
    private HttpStatus status;

    public ModelAndView(String viewName, Model model, HttpStatus status) {
        this.viewName = viewName;
        this.model = model;
        this.status = status;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public boolean containsAttribute(String key) {
        return model.containsAttribute(key);
    }

    public Object getAttribute(String key) {
        return model.getAttribute(key);
    }
}
