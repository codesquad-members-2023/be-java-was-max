package webserver.frontcontroller;

public class ModelAndView {

    private String viewName;
    private Model model;

    public ModelAndView(String viewName, Model model) {
        this.viewName = viewName;
        this.model = model;
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

    public boolean containsAttribute(String key) {
        return model.containsAttribute(key);
    }

    public Object getAttribute(String key) {
        return model.getAttribute(key);
    }
}
