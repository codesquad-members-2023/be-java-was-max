package webserver.request.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import webserver.request.common.HttpMethod;
import webserver.request.component.RequestHeader;
import webserver.request.component.RequestLine;
import webserver.request.component.RequestParameter;
import webserver.request.component.RequestURI;
import webserver.response.HttpVersion;
import webserver.util.HttpRequestUtil;

public final class HttpRequestParser {

    private static final String REQUEST_LINE_SEPARATOR = "\\s";
    private static final String SEPARATOR = ":\\s*";
    private static final String QUERYSTRING_SEPARATOR = "\\?";

    private HttpRequestParser() {

    }

    public static RequestLine parseRequestLine(String requestLine) {
        String[] tokens = requestLine.split(REQUEST_LINE_SEPARATOR);
        HttpMethod method = HttpMethod.valueOf(tokens[0]);
        String[] requestURITokens = tokens[1].split(QUERYSTRING_SEPARATOR);
        double httpVersion = Double.parseDouble(tokens[2].split("/")[1]);
        String path = requestURITokens[0];

        RequestParameter requestParameter = new RequestParameter(new HashMap<>());
        if (requestURITokens.length >= 2) {
            Map<String, String> parameter = HttpRequestUtil.parseQueryString(requestURITokens[1]);
            requestParameter.addParameter(parameter);
        }

        RequestURI requestURI = new RequestURI(path, requestParameter);
        return new RequestLine(method, requestURI, new HttpVersion(httpVersion));
    }

    public static RequestHeader parseHeader(BufferedReader br) throws IOException {
        Map<String, Object> header = new HashMap<>();
        String line;
        while (!(line = br.readLine()).isEmpty()) {
            String[] tokens = line.split(SEPARATOR);
            String key = tokens[0].trim();
            String value = tokens[1].trim();
            header.put(key, value);
        }
        return new RequestHeader(header);
    }
}
