package util.request;

import java.util.Map;

public class HttpRequest {

    private final String url;

    private final String method;

    private final String queryString;

    private final Map<String,String> queryParam;

    public HttpRequest(String requestLine) {
        this.url = parseUrl(requestLine);
        this.method = getMethod(requestLine);
        this.queryString = createQueryString();
        this.queryParam = HttpRequestUtil.paresQueryString(queryString);
    }

    private String parseUrl(String requestLine){
        return requestLine.split(" ")[1];
    }

    private String getMethod(String requestLine){
        return requestLine.split(" ")[0];
    }

    private String createQueryString() {
        String[] query = url.split("\\?");
        return query.length > 1 ? query[1] : "";
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getQueryString() {
        return queryString;
    }

    public Map<String, String> getQueryParam() {
        return queryParam;
    }
}
