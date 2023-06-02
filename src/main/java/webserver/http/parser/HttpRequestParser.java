package webserver.http.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.common.header.HeaderType;
import webserver.http.request.HttpRequest;
import webserver.http.request.component.RequestHeader;
import webserver.http.request.component.RequestLine;
import webserver.http.request.component.RequestMessageBody;
import webserver.http.request.component.RequestQueryString;
import webserver.http.session.Cookie;
import webserver.http.session.HttpSession;
import webserver.http.session.SessionContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static webserver.http.common.header.EntityHeaderType.CONTENT_LENGTH;
import static webserver.http.common.header.RequestHeaderType.COOKIE;
import static webserver.http.parser.HttpParser.parseHeaderMap;
import static webserver.http.parser.HttpParser.readHttpHeader;

public final class HttpRequestParser {

    public static final String KEY_VALUE_SEPARATOR = "=";
    public static final String QUERYSTRING_SEPARATOR = "&";
    public static final String HEADER_SEPARATOR_REGEX = ":\\s+";
    public static final String REQUEST_LINE_SEPARATOR_REGEX = "\\s";
    public static final String QUERYSTRING_SEPARATOR_REGEX = "[?\\s]";
    public static final int HTTP_METHOD_INDEX = 0;
    public static final int PATH_INDEX = 0;
    public static final int REQUEST_URI_INDEX = 1;
    public static final int HTTP_VER_INDEX = 2;
    public static final int QUERYSTRING_INDEX = 2;
    public static final int KEY_INDEX = 0;
    public static final int VALUE_INDEX = 1;
    private static final String EMPTY = "";
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);

    private HttpRequestParser() {

    }

    public static HttpRequest parseHttpRequest(BufferedReader br) throws IOException {
        String requestLineString = br.readLine();
        String headerString = readHttpHeader(br);

        RequestLine requestLine = RequestLine.parseRequestLine(requestLineString);
        RequestQueryString queryString = parseQueryString(requestLineString);
        RequestHeader requestHeader = parseRequestHeader(headerString);
        HttpSession httpSession = parseHttpSession(headerString);
        int contentLength = getContentLength(requestHeader);
        RequestMessageBody requestMessageBody =
                RequestMessageBody.parseMessageBody(HttpParser.readMessageBody(br, contentLength));
        return new HttpRequest(requestLine, requestHeader, queryString, requestMessageBody, httpSession);
    }

    private static RequestHeader parseRequestHeader(String headerString) {
        return new RequestHeader(parseHeaderMap(headerString));
    }

    private static HttpSession parseHttpSession(String headerString) {
        Map<HeaderType, String> headerMap = parseHeaderMap(headerString);
        List<Cookie> cookies = Cookie.parse(headerMap.get(COOKIE));
        String sid = cookies.stream()
                .filter(cookie -> cookie.getName().equals("sid"))
                .map(Cookie::getValue)
                .findAny().orElse(null);
        return SessionContainer.get(sid);
    }

    private static RequestQueryString parseQueryString(String requestLine) {
        String[] tokens = requestLine.split(QUERYSTRING_SEPARATOR_REGEX);
        String queryString = EMPTY;
        if (tokens.length >= 4) {
            queryString = tokens[QUERYSTRING_INDEX];
        }
        return RequestQueryString.parseQueryString(queryString);
    }

    private static int getContentLength(RequestHeader requestHeader) {
        return Integer.parseInt(requestHeader.get(CONTENT_LENGTH).orElse("0"));
    }
}
