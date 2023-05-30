package servlet.domain.request.target;

import java.util.Map;

// An absolute path, ultimately followed by a '?' and query string.
// This is the most common form, known as the origin form, and is used with GET, POST, HEAD, and OPTIONS methods.
// POST / HTTP/1.1
// GET /background.png HTTP/1.0
// HEAD /test.html?query=alibaba HTTP/1.1
// OPTIONS /anypage.html HTTP/1.0
public class OriginForm {
    public static final int PATH_INDEX = 0;
    public static final int QUERY_STRING_INDEX = 1;
    public static final String PATH_QUERYSTRING_SPIT_DELIMITER = "\\?";
    public static final String PATH_QUERYSTRING_DELIMITER = "?";
    public static final int NONE = 0;
    private final Path path;
    private QueryString queryString;

    private OriginForm(Path path) {
        this.path = path;
    }

    private OriginForm(Path path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static OriginForm from(String requestTarget) {
        if (hasQuery(requestTarget)) {
            String[] target = requestTarget.split(PATH_QUERYSTRING_SPIT_DELIMITER);
            return new OriginForm(Path.of(target[PATH_INDEX]), QueryString.of(target[QUERY_STRING_INDEX]));
        }
        return new OriginForm(Path.of(requestTarget));
    }

    private static boolean hasQuery(String url) {
        return url.contains(OriginForm.PATH_QUERYSTRING_DELIMITER);
    }

    public Path getPath() {
        return path;
    }

    public String getPathValue() {
        return path.getValue();
    }

    public Map<String, String> getParameters() {
        return queryString.getQueryParamMap();
    }

    public boolean isSamePath(String path) {
        return this.path.isSame(path);
    }

    public boolean isSameParameterCount(int parameterCount) {
        if (queryString != null) {
            return queryString.isSameCount(parameterCount);
        }
        return parameterCount == NONE;
    }

    public boolean hasParameter() {
        return queryString != null;
    }

    public boolean containsParam(String key, String value) {
        return queryString.contains(key, value);
    }

    public boolean startsWith(String path) {
        return this.path.startsWith(path);
    }
}
