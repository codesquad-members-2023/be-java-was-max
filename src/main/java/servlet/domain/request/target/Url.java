package servlet.domain.request.target;

public class Url {
    private final Path path;

    private final String prefix;

    public Url(Path path, String prefix) {
        this.path = path;
        this.prefix = prefix;
    }

    public Path getPath() {
        return path;
    }

    public String getPrefix() {
        return prefix;
    }
}
