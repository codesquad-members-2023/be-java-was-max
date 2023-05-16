package container.domain.request.target;

// An absolute path, ultimately followed by a '?' and query string.
// This is the most common form, known as the origin form, and is used with GET, POST, HEAD, and OPTIONS methods.
// POST / HTTP/1.1
// GET /background.png HTTP/1.0
// HEAD /test.html?query=alibaba HTTP/1.1
// OPTIONS /anypage.html HTTP/1.0
public class OriginForm {
    private final Path path;
    private final QueryString queryString;

    public OriginForm(Path path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public Path getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }
}
