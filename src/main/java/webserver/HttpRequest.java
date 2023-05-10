package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Map<String, String> request;
    private StringBuilder requestHeader;
    private String contentType;

    public HttpRequest(){}

    public HttpRequest(InputStream in) throws IOException {
        this.request = new HashMap<>();
        this.requestHeader = new StringBuilder();
        makeRequest(in);
    }

    private void makeRequest(InputStream in) throws IOException { // request와 requestHeader 만들기
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = br.readLine();  // method, url, httpVersion
        String[] splitedLine = line.split(" ");
        String url = splitedLine[1];
        request.put("Method", splitedLine[0]);
        request.put("Url", url);
        request.put("HttpVersion", splitedLine[2]);

        if (url.contains("/user/create")) { // 회원 가입 페이지에서 회원 정보를 받으면 실행
            Database.addUser(createUser(url)); // Database에 생성한 user 추가
        }

        while (!(line = br.readLine()).equals("")) { // Request Header
            splitedLine = line.split(": ");
            request.put(splitedLine[0], splitedLine[1]); // Map에 request header 넣기
        }

        makeRequestHeader(request);
    }

    private void makeRequestHeader(Map<String, String> request){
        requestHeader.append(request.get("Method")).append(" ");
        requestHeader.append(request.get("Url")).append(" ");
        requestHeader.append(request.get("HttpVersion")).append("\n");
        requestHeader.append("Host: ").append(request.get("Host")).append("\n");
        requestHeader.append("Connection: ").append(request.get("Connection")).append("\n");
        requestHeader.append("Accept: ").append(request.get("Accept"));
    }

    private User createUser(String url){
        String[] splitedUrl = url.split("\\?");
        HttpRequestUtils utils = new HttpRequestUtils();
        Map<String, String> parsedStr = utils.parseQueryString(splitedUrl[1]);
        return utils.createUser(parsedStr);
    }

    public String getRequestHeader(){
        return requestHeader.toString();
    }

    public void getRequests(Map<String, String> request){
        for (String key: request.keySet()){
            logger.debug("{}: {}", key, request.get(key));
        }
    }

    public String getUrl(){
        return request.get("url");
    }

    public String getContentType(){
        return contentType;
    }
}
