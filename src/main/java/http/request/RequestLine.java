package http.request;

import java.util.Optional;

public class RequestLine {
    private final String method; // Method
    private final String requestUri; // Request-URI
    private final String httpVersion; // HTTP-Version

    public RequestLine(String requestLine){
        String[] splitedLine = requestLine.split(" ");
        this.method = splitedLine[0];
        this.requestUri = splitedLine[1];
        this.httpVersion = splitedLine[2];
    }

    public String getMethod() {
        return method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getPath(){
        return requestUri.split("\\?")[0];
    }

    public Optional<String> getQueryString(){
        return Optional.ofNullable(requestUri.split("\\?")[1]);
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String get(){
        return method + " " + requestUri + " " + httpVersion;
    }
}
