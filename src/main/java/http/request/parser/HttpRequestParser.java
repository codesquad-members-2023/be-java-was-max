package http.request.parser;

import http.common.HttpMethod;
import http.common.version.HttpVersion;
import http.common.version.ProtocolVersion;
import http.request.HttpRequest;
import http.request.component.RequestHeader;
import http.request.component.RequestLine;
import http.request.component.RequestMessageBody;
import http.request.component.RequestParameter;
import http.request.component.RequestURI;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpRequestParser {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);


    private static final String REQUEST_LINE_SEPARATOR = "\\s";
    private static final String SEPARATOR = ":\\s*";
    private static final String QUERYSTRING_SEPARATOR = "\\?";

    private HttpRequestParser() {

    }

    public static HttpRequest parse(BufferedReader br) throws IOException {
        RequestLine requestLine = parseRequestLine(br.readLine());
        RequestHeader requestHeader = parseHeader(br);
        logger.debug("requestHeader : {}", requestHeader);
        int contentType = 0;
        if (requestHeader.containsKey("Content-Length")) {
            contentType = Integer.parseInt(requestHeader.getHeader("Content-Length"));
        }
        RequestMessageBody requestMessageBody = parseRequestMessageBody(br, contentType);
        return new HttpRequest(requestLine, requestHeader, requestMessageBody);
    }

    private static RequestLine parseRequestLine(String requestLine) {
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

    private static RequestHeader parseHeader(BufferedReader br) throws IOException {
        Map<String, String> header = new HashMap<>();
        String line;
        while (!(line = br.readLine()).isEmpty()) {
            logger.debug("line : {}", line);
            String[] tokens = line.split(SEPARATOR);
            String key = tokens[0].trim();
            String value = tokens[1].trim();
            header.put(key, value);
        }
        return new RequestHeader(header);
    }

    private static Map<String, String> parseQueryString(String queryString) {
        if (queryString.equals("")) {
            return new HashMap<>();
        }

        Map<String, String> paramMap = new HashMap<>();
        String[] params = queryString.split("&");
        for (String param : params) {
            String[] split = param.split("=");
            paramMap.put(split[0], split[1]);
        }
        return paramMap;
    }

    private static RequestMessageBody parseRequestMessageBody(BufferedReader br, int contentType)
        throws IOException {
        char[] buf = new char[contentType];
        br.read(buf, 0, contentType);
        return new RequestMessageBody(parseQueryString(String.valueOf(buf)));
    }
}
