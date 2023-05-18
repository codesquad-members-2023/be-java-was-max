package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
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
    }

    private void makeRequests(BufferedReader br) throws IOException { // requestLine, requestHeader, requestBody 만들기
        // TODO:
        //  1. Method가 Get 일 때 : path를 필터링해서 User 생성하는 역할
        //  2. Method가 Post 일 때 : requestBody를 필터링해서 User 생성하는 역할
        // TODO:
        //  1. 생성된 Path로 파일 존재 확인하고 경로 찾기
        //  2. 확장자 enum 만들기

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

    public StringBuilder getRequestBody(){
        return requestBody.getContents().orElseThrow(() -> new IllegalArgumentException("입력이 올바르지 않습니다."));
    }

}
