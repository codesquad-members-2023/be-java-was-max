package http.request;

import http.common.HttpMethod;
import http.common.HttpVersion;
import http.common.ProtocolVersion;
import http.request.component.RequestHeader;
import http.request.component.RequestLine;
import http.request.component.RequestParameter;
import http.request.component.RequestURI;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        String[] protocolVersionTokens = tokens[2].split("[/.]");
        int protocolMajor = Integer.parseInt(protocolVersionTokens[1]);
        int protocolMinor = Integer.parseInt(protocolVersionTokens[2]);
        String path = requestURITokens[0];

        RequestParameter requestParameter = new RequestParameter(new HashMap<>());
        if (requestURITokens.length >= 2) {
            Map<String, String> parameter = HttpRequestParser.parseQueryString(requestURITokens[1]);
            requestParameter.addParameter(parameter);
        }

        RequestURI requestURI = new RequestURI(path, requestParameter);
        ProtocolVersion protocolVersion = new HttpVersion(protocolMajor, protocolMinor);
        return new RequestLine(method, requestURI, protocolVersion);
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

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> paramMap = new HashMap<>();
        String[] params = queryString.split("&");
        for (String param : params) {
            String[] split = param.split("=");
            paramMap.put(split[0], split[1]);
        }
        return paramMap;
    }
}
