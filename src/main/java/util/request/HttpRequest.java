package util.request;

public class HttpRequest {

    private final String url;

    private final HttpHeader headers;

    private final Session session;

    public HttpRequest(HttpHeader header) {
        this.url = parseUrl(header.getRequestLine());
        this.headers = header;
        this.session = new Session();
    }

    private String parseUrl(String requestLine){
        return requestLine.split(" ")[1];
    }
    private String getMethod(String requestLine){
        return requestLine.split(" ")[0];
    }
    public String getUrl() {
        return url;
    }

    public HttpHeader getHeaders() {
        return headers;
    }

    public Session Session() {
        return session;
    }
}
