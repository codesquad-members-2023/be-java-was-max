package model;

import util.RequestParser;

import java.util.HashMap;
import java.util.Map;

public class Headers {

    private static final int HEADER_INDEX = 0;
    private static final int CONTENT_INDEX = 1;
    private final Map<String, String> headers;

    public Headers() {
        this.headers = new HashMap<>();
    }

    public void add(String rawHeader) {
        String[] parsedHeader = RequestParser.parseHeader(rawHeader);

        headers.put(parsedHeader[HEADER_INDEX], parsedHeader[CONTENT_INDEX]);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
