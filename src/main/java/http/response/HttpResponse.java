package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
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

    public Optional<String> getContentType(){
        return responseHeaders.get("Content-Type");
    }

    public Optional<String> getContentLength(){
        return responseHeaders.get("Content-Length");
    }

    public String getStatusCode(){
        return statusLine.getStatusCode();
    }

    public Optional<String> getLocation() {
        return responseHeaders.get("Location");
    }

    public void logResponse(){
        logger.debug("✅*******Response*******");
        logger.debug("<Status Line>");
        logger.debug(statusLine.get()); // requestLine
        logger.debug("<Response Headers>");
        Map<String, String> responseHeadersMap = responseHeaders.get();
        for (String key: responseHeadersMap.keySet()){
            logger.debug("{}: {}", key, responseHeadersMap.get(key)); // requestHeader
        }
        // TODO: 바이트 코드 문자열로 수정하기
//        logger.debug("<Response Body>");
//        logger.debug(Arrays.toString(responseBody.get())); // requestBody
    }
}
