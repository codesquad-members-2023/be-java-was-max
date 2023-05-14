package http.request.component;

public class RequestURI {

    private final String path;
    private final RequestParameter parameter;

    public RequestURI(String path, RequestParameter parameter) {
        this.path = path;
        this.parameter = parameter;
    }

    public String getPath() {
        return path;
    }

    public RequestParameter getParameter() {
        return parameter;
    }

    @Override
    public String toString() {
        return "RequestURI{" +
            "path='" + path + '\'' +
            ", parameter=" + parameter +
            '}';
    }
}
