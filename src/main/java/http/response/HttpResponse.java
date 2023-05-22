package http.response;

import java.util.Map;

public class HttpResponse {
    private StatusLine statusLine;
    private final ResponseHeaders responseHeaders;
    private final ResponseBody responseBody;

    public HttpResponse() {
        this.responseHeaders = new ResponseHeaders();
        this.responseBody = new ResponseBody();
    }

    public void setStatusLine(String line){
        statusLine = new StatusLine(line);
    }

    public void setResponseHeaders(String line){
        responseHeaders.add(line);
    }

    public void setResponseBody(byte[] body){
        responseBody.add(body);
    }

    public String getStatusLine() {
        return statusLine.get();
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders.get();
    }

    public byte[] getResponseBody() {
        return responseBody.get();
    }
}
