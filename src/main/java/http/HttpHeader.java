package http;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.LOCATION;
import static com.google.common.net.HttpHeaders.SET_COOKIE;

public class HttpHeader {
    private final Map<String, String> headers;

    public HttpHeader() {
        this.headers = Maps.newHashMap();
    }
    public HttpHeader(List<String> headers) {
        this.headers = HttpUtil.parseHeader(headers);
    }

    public boolean contains(String header) {
        return this.headers.containsKey(header);
    }

    public String findFieldByName(String name) {
        return this.headers.get(name);
    }

    public void addContentType(String field) {
        this.headers.put(CONTENT_TYPE, field);
    }
    public void addSetCookie(String field) {
        this.headers.put(SET_COOKIE, field);
    }
    public void addLocation(String field) {
        this.headers.put(LOCATION, field);
    }

    @Override
    public String toString() {
        return "HttpHeader[ Content-Type : " + headers.get(CONTENT_TYPE) + " Set-Cookie : " + headers.get(SET_COOKIE) + " Location : " + headers.get(LOCATION) + " ]";
    }
}
