package was.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {
    private static final String COLON = ":";
    private static final String COMMA = ",";

    private final Map<String, List<String>> headers;

    protected HttpHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public static HttpHeaders parse(final List<String> headerLines) {
        final Map<String, List<String>> headers = new LinkedHashMap<>();

        for (String headerLine : headerLines) {
            String key = headerLine.split(COLON)[0];
            List<String> values = parseHeaderValues(headerLine.replaceFirst(key + COLON, ""));

            headers.put(HeaderType.keyOf(key), values);
        }

        return new HttpHeaders(headers);
    }

    public List<String> getHeader(String header) {
        return new ArrayList<>(headers.getOrDefault(header, Collections.emptyList()));
    }

    public int getContentLength() {
        List<String> contentLength = headers.getOrDefault(HeaderType.CONTENT_LENGTH.getKey(), Collections.emptyList());

        if (contentLength.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(contentLength.get(0));
    }

    private static List<String> parseHeaderValues(String headerValues) {
        return Arrays.stream(headerValues.split(COMMA))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HttpHeaders { ");

        for (var entry : headers.entrySet()) {
            sb.append("\n").append('\t').append(entry.getKey()).append(": ").append(entry.getValue());
        }

        sb.append("\n").append("}");

        return sb.toString();
    }
}
