package model;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private final Map<String, String> requestLine = new HashMap<>();
    private final Map<String, String> header = new HashMap<>();
    private String body;

    public Map<String, String> getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public void addRequestLine(Map<String, String> requestLine) {
        this.requestLine.putAll(requestLine);
    }

    public void addHeader(Map<String, String> header) {
        this.header.putAll(header);
    }

    public void setBody(String body) {
        this.body = body;
    }
}
