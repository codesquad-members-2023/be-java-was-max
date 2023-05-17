package servlet.domain.request.target;

// An absolute path, ultimately followed by a '?' and query string.
// This is the most common form, known as the origin form, and is used with GET, POST, HEAD, and OPTIONS methods.
// POST / HTTP/1.1
// GET /background.png HTTP/1.0
// HEAD /test.html?query=alibaba HTTP/1.1
// OPTIONS /anypage.html HTTP/1.0
public class OriginForm {
    private final Path path;
    private QueryString queryString;

    public OriginForm(Path path) {
        this.path = path;
    }

    public OriginForm(Path path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static OriginForm parse(String requestTarget) {
        if (hasQuery(requestTarget)) {
            String[] target = requestTarget.split("\\?");
            return new OriginForm(Path.of(target[0]), QueryString.of(target[1]));
        }
        return new OriginForm(Path.of(requestTarget));
    }

    private static boolean hasQuery(String url) {
        return url.contains("?");
    }

    public Path getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }
}
