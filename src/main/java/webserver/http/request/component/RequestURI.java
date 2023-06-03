package webserver.http.request.component;

public class RequestURI {

    private final String path;

    public RequestURI(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }
}
