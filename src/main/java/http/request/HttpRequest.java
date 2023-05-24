package http.request;

import static http.common.header.RequestHeaderType.COOKIE;

import http.common.HttpMethod;
import http.common.header.HeaderType;
import http.common.version.ProtocolVersion;
import http.request.component.RequestHeader;
import http.request.component.RequestLine;
import http.request.component.RequestMessageBody;
import http.request.component.RequestQueryString;
import http.request.component.RequestURI;
import http.session.Cookie;
import http.session.HttpSession;
import http.session.SessionContainer;
import java.util.List;
import java.util.Optional;
import webserver.frontcontroller.RequestDispatcher;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestQueryString queryString;
    private final RequestMessageBody messageBody;
    private HttpSession httpSession;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestQueryString queryString,
        RequestMessageBody messageBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.queryString = queryString;
        this.messageBody = messageBody;
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public RequestURI getRequestURI() {
        return requestLine.getRequestURI();
    }

    public String getPath() {
        return getRequestURI().getPath();
    }

    public ProtocolVersion getProtocolVersion() {
        return requestLine.getProtocolVersion();
    }

    public Optional<String> get(HeaderType key) {
        return requestHeader.get(key);
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

    public void setAttribute(String key, Object value) {
        queryString.getParameter().put(key, value.toString());
    }

    public boolean hasHttpSession() {
        return httpSession != null;
    }

    public HttpSession getHttpSession() {
        if (httpSession != null) {
            return httpSession;
        }

        httpSession = SessionContainer.getSession(getSid());
        return httpSession;
    }

    public String getSid() {
        String cookieString = requestHeader.get(COOKIE).orElse(null);
        List<Cookie> cookies = Cookie.parse(cookieString);
        return cookies.stream()
            .filter(cookie -> cookie.getName().equals("sid"))
            .map(Cookie::getValue)
            .findAny().orElse(null);
    }


    @Override
    public String toString() {
        return String.join("\r\n", requestLine.toString(), requestHeader.toString(), messageBody.toString());
    }

    // TODO
    public RequestDispatcher getRequestDispatcher(String viewPath) {
        return null;
    }
}
