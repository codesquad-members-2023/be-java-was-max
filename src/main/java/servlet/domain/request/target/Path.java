package servlet.domain.request.target;

public class Path {
    private final String value;

    private Path(String value) {
        this.value = value;
    }

    public static Path of(String requestTarget) {
        return new Path(requestTarget);
    }

    public String getValue() {
        return value;
    }

    public boolean isSame(String path) {
        return this.value.equals(path);
    }

    public boolean startsWith(String path) {
        return value.startsWith(path);
    }
}
