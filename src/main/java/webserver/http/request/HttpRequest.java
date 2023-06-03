package webserver.http.request;

import webserver.frontcontroller.RequestDispatcher;
import webserver.http.request.component.RequestHeader;
import webserver.http.request.component.RequestLine;
import webserver.http.request.component.RequestMessageBody;
import webserver.http.request.component.RequestQueryString;
import webserver.http.session.Cookie;
import webserver.http.session.HttpSession;
import webserver.http.session.SessionContainer;
import webserver.util.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static webserver.http.common.header.RequestHeaderType.COOKIE;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestQueryString queryString;
    private final RequestMessageBody messageBody;
    private final Map<String, Object> attributes;
    private HttpSession httpSession;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestQueryString queryString,
                       RequestMessageBody messageBody, HttpSession httpSession) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.queryString = queryString;
        this.messageBody = messageBody;
        this.attributes = new HashMap<>();
        this.httpSession = httpSession;
    }

    public RequestMessageBody getMessageBody() {
        return messageBody;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestQueryString getQueryString() {
        return queryString;
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public boolean hasHttpSession() {
        return httpSession != null;
    }

    public HttpSession getHttpSession() {
        if (httpSession != null) {
            return httpSession;
        }

        httpSession = SessionContainer.getSession(getSessionId());
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public String getSessionId() {
        String cookieString = requestHeader.get(COOKIE).orElse(null);
        List<Cookie> cookies = Cookie.parse(cookieString);
        return cookies.stream()
                .filter(cookie -> cookie.getName().equals("sid"))
                .map(Cookie::getValue)
                .findAny().orElse(null);
    }

    public RequestDispatcher getRequestDispatcher(String viewPath) {
        File file = FileUtils.getFileFromPath(viewPath).orElse(null);
        return new RequestDispatcher(file, viewPath);
    }

    @Override
    public String toString() {
        return String.join("\r\n", requestLine.toString(), requestHeader.toString(), messageBody.toString());
    }


}
