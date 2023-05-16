package model;

import java.util.Map;

public class RequestLine {
    private final String method;
    private final ContentType contentType;
    private final String path;
    private final String url;
    private final Map<String, String> queryMap;

    public RequestLine(String method, String url, Map<String, String> queryMap) {
        this.method = method;
        this.url = url;
        this.contentType = ContentType.findByUrl(url);
        this.path = contentType.separatePath(url);
        this.queryMap = queryMap;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getQueryMap() {
        return queryMap;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getPath() {
        return path;
    }

    public String getUrl() {
        return url;
    }
}
