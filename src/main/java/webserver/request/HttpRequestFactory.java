package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class HttpRequestFactory {

    private static final String EOF = "";
    private static final String REQUEST_LINE_SEPARATOR = " ";
    private static final String SEPARATOR = ":\\s*";

    private HttpRequestFactory() {

    }

    public static HttpRequest createHttpRequest(BufferedReader br) throws IOException {
        RequestLine requestLine = parsingRequestLine(br);
        Map<String, String> header = parsingHeader(br);
        return new HttpRequest(requestLine, header);
    }

    private static RequestLine parsingRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        String[] requestLineSplit = requestLine.split(REQUEST_LINE_SEPARATOR);
        String method = requestLineSplit[0];
        String[] pathAndQueryString = requestLineSplit[1].split("\\?");
        String httpVersion = requestLineSplit[2];

        String requestURI = pathAndQueryString[0];

        RequestParameter requestParameter = new RequestParameter(new HashMap<>());
        if (pathAndQueryString.length >= 2) {
            String queryString = pathAndQueryString[1];
            Map<String, String> parameter = HttpRequestUtil.parseQueryString(queryString);
            requestParameter = new RequestParameter(parameter);
        }

        return new RequestLine(method, requestURI, requestParameter, httpVersion);
    }

    private static Map<String, String> parsingHeader(BufferedReader br) throws IOException {
        Map<String, String> header = new HashMap<>();
        String line;
        while (!(line = br.readLine()).equals(EOF)) {
            String[] split = line.split(SEPARATOR);
            String key = split[0].trim();
            String value = split[1].trim();
            header.put(key, value);
        }
        return header;
    }
}
