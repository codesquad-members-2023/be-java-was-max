package webserver.request;

import java.util.Collections;
import java.util.Map;

public class HttpRequest {

    private final RequestLine requestLine;
    private final Map<String, String> header;

    public HttpRequest(RequestLine requestLine, Map<String, String> header) {
        this.requestLine = requestLine;
        this.header = header;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeader() {
        return Collections.unmodifiableMap(header);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(requestLine.toString());
        sb.append("\r\n");
        for (String key : header.keySet()) {
            sb.append(key).append(": ").append(header.get(key)).append("\r\n");
        }
        return sb.toString().trim();
    }
}
