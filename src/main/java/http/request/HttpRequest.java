package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private RequestLine requestLine;
    private RequestHeaders requestHeaders;
    private RequestBody requestBody;

    public HttpRequest(){}

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.requestLine = new RequestLine(br.readLine()); // Request Line: method, path, httpVersion
        this.requestHeaders = new RequestHeaders();
        this.requestBody = new RequestBody();
        makeRequests(br);
        requestLog();
    }

    private void makeRequests(BufferedReader br) throws IOException { // requestLine, requestHeader, requestBody 만들기
        String line = br.readLine();
        while (!line.equals("")) { // Request Header
            requestHeaders.addHeader(line);
            line = br.readLine();
        }

        if (requestLine.getMethod().equals(HttpMethod.POST)){ // POST 일 때만 Request Body 가져오기
            for (int i = 0; i < Long.parseLong(requestHeaders.get("Content-Length")); i++) { // Request Body
                requestBody.addContent((char) br.read());
            }
        }
    }

    public String getPath(){
        return requestLine.getPath();
    }

    public Optional<String> getQueryString(){
        return requestLine.getQueryString();
    }
    public String getMethod(){
        return requestLine.getMethod();
    }

    public String getRequestBody(){
        return requestBody.getContents();
    }

    public String getContentType(String mappingUri){
        return requestHeaders.getContentType(mappingUri);
    }

    public void requestLog(){
        logger.debug("<Request Line>");
        logger.debug(requestLine.get()); // requestLine
        logger.debug("<Request Headers>");
        Map<String, String> requestHeadersMap = requestHeaders.get();
        for (String key: requestHeadersMap.keySet()){
            logger.debug("{}: {}", key, requestHeadersMap.get(key)); // requestHeader
        }
        logger.debug("<Request Body>");
        logger.debug(requestBody.getContents()); // requestBody
    }

}
