package webserver.util;

import model.ContentType;
import model.RequestLine;

import java.util.HashMap;
import java.util.Map;

import static model.RequestLine.AMPERSAND;
import static model.RequestLine.EQUALS_MARK;

public class HttpRequestUtils {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final int PARAM_NAME_IDX = 0;
    private static final int PARAM_VALUE_IDX = 1;
    private static final String COOKIE = "Cookie";
    private static final String SEMI_COLON = ";";
    private static final String EQUALS_SEPARATOR = "=";

    private final RequestLine requestLine;
    private final Map<String, String> headers;
    private final String messageBody;
    private final Map<String, String> parameters;
    private final Map<String, String> cookies;

    public HttpRequestUtils(String requestLine, Map<String, String> headers, String messageBody) {
        this.requestLine = new RequestLine(requestLine);
        this.headers = headers;
        this.messageBody = messageBody;
        this.parameters = setParameters();
        this.cookies = setCookies();
    }

    private Map<String, String> setParameters() {
        if (requestLine.getMethod().equals(GET)) {
            return requestLine.getQueryMap();
        }

        if (requestLine.getMethod().equals(POST)) {
            return parseParametersBy(messageBody);
        }

        return null;
    }

    private Map<String, String> parseParametersBy(String messageBody) {
        HashMap<String, String> parameters = new HashMap<>();

        if (messageBody == null) {
            return null;
        }

        String[] parameterPairs = messageBody.split(AMPERSAND);
        for (String pair : parameterPairs) {
            parameters.put(pair.split(EQUALS_MARK)[PARAM_NAME_IDX], pair.split(EQUALS_MARK)[PARAM_VALUE_IDX]);
        }

        return parameters;
    }

    private Map<String, String> setCookies() {
        if (!headers.containsKey(COOKIE)) {
            return null;
        }

        Map<String, String> cookies = new HashMap<>();

        String[] splitCookies = headers.get(COOKIE).split(SEMI_COLON);
        for (String cookie : splitCookies) {
            String cookieName = cookie.split(EQUALS_SEPARATOR)[PARAM_NAME_IDX].trim();
            String cookieValue = cookie.split(EQUALS_SEPARATOR)[PARAM_VALUE_IDX].trim();
            cookies.put(cookieName, cookieValue);
        }

        return cookies;
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public String getUrl() {
        return requestLine.getUrl();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }
}
