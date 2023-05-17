package servlet.domain.request.target;

public class Path {
    private final String path;

    private Path(String path) {
        this.path = path;
    }

    public static Path of(String requestTarget) {
        return new Path(requestTarget);
    }

    public String getPath() {
        return path;
    }
}
