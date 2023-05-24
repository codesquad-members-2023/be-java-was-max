package webserver;

import webserver.util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestSeparator {
    private static final int HEADER_NAME_IDX = 0;
    private static final int HEADER_VALUE_IDX = 1;
    private static final String CONTENT_LENGTH = "Content-Length";

    public static HttpRequestUtils askHttpRequest(final BufferedReader br) throws IOException {
        String requestLine = getRequestLine(br);
        Map<String, String> headers = parseHeaders(br);
        String messageBody = getMessageBody(br, headers);

        return new HttpRequestUtils(requestLine, headers, messageBody);
    }

    private static String getRequestLine(final BufferedReader br) throws IOException {
        return br.readLine();
    }

    private static Map<String, String> parseHeaders(final BufferedReader br) throws IOException {
        HashMap<String, String> headers = new HashMap<>();

        String line = br.readLine();
        while (!line.equals("")) {
            addHeader(line, headers);
            line = br.readLine();
        }

        return headers;
    }

    private static String getMessageBody(final BufferedReader br, Map<String, String> headers) throws IOException {
        if (!headers.containsKey(CONTENT_LENGTH)) {
            return null;
        }

        int contentLength = Integer.parseInt(headers.get(CONTENT_LENGTH));
        char[] messageBody = new char[contentLength];
        br.read(messageBody);
        return String.valueOf(messageBody);
    }

    private static void addHeader(String line, Map<String, String> headers) {
        String[] splitLine = line.split(":", 2);
        String headerName = splitLine[HEADER_NAME_IDX].trim();
        String headerValue = splitLine[HEADER_VALUE_IDX].trim();
        headers.put(headerName, headerValue);
    }
}
