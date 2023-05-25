package model;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeaders {

    private static final String CRLF = "\r\n";
    private final Map<String, String> headers;

    public ResponseHeaders() {
        this.headers = new HashMap<>();
    }

    public void add(String field, String value) {
        headers.put(field, value);
    }

    public byte[] toBytes() {
        return toString().getBytes();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        headers.forEach((key, value) -> sb.append(key + ": " + value + CRLF));
        sb.append(CRLF);
        return sb.toString();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
