package model;

import util.RequestParser;

public class RequestLine {

    private final static int METHOD_INDEX = 0;
    private final static int URL_INDEX = 1;
    private final static int PROTOCOL_INDEX = 2;
    private final Method method;
    private final String url;
    private final String protocol;

    public RequestLine(String rawRequestLine) {
        String[] parsedRequestLine = RequestParser.parseRequestLine(rawRequestLine);

        this.method = Method.valueOf(parsedRequestLine[METHOD_INDEX]);
        this.url = parsedRequestLine[URL_INDEX];
        this.protocol = parsedRequestLine[PROTOCOL_INDEX];
    }

    public boolean hasBody() {
        return method.hasBody();
    }

    public Method getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }
}
