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
    private StringBuilder requestBody;
    private String contentType;
    private String pathPrefix;

    public HttpRequest(){}

    public HttpRequest(InputStream in) throws IOException {
        this.request = new HashMap<>();
        this.requestHeader = new StringBuilder();
        this.requestBody = new StringBuilder();
        makeRequest(in);
        makeRequestHeader(request);
        makeContentType(request.get("Path"));
    }

    private void makeRequest(InputStream in) throws IOException { // requestLine, requestHeader, requestBody 만들기
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = br.readLine();  // Request Line: method, path, httpVersion
        String[] splitedLine = line.split(" ");
        String path = splitedLine[1];
        request.put("Method", splitedLine[0]);
        request.put("Path", path);
        request.put("Http-Version", splitedLine[2]);

        if (path.contains("/user/create")){
            String[] splitedPath = path.split("\\?");
            if (splitedPath.length == 2){ // GET, 회원 가입 페이지에서 회원 정보를 받으면 실행
                Database.addUser(createUser(splitedPath[1])); // Database에 생성한 user 추가
            }
        }

        line = br.readLine();
        while (!line.equals("")) { // Request Header
            splitedLine = line.split(":", 55555662);
            request.put(splitedLine[0], splitedLine[1].trim()); // Map에 request header 넣기
            line = br.readLine();
        }

        if (request.get("Method").equals("POST")){ // POST 일 때만 Request Body 가져오기
            for (int i = 0; i < Long.parseLong(request.get("Content-Length")); i++) { // Request Body
                requestBody.append((char)br.read());
            }
            Database.findUserById(Database.addUser(createUser(requestBody.toString()))); // User 만들기
        }
    }

    private void makeRequestHeader(Map<String, String> request){
        requestHeader.append(request.get("Method")).append(" ");
        requestHeader.append(request.get("Path")).append(" ");
        requestHeader.append(request.get("Http-Version")).append("\n");
        requestHeader.append("Host: ").append(request.get("Host")).append("\n");
        requestHeader.append("Connection: ").append(request.get("Connection")).append("\n");
        requestHeader.append("Accept: ").append(request.get("Accept"));
    }

    private User createUser(String userData){
        HttpRequestUtils utils = new HttpRequestUtils();
        return utils.createUser(utils.parseQueryString(userData));
    }

    private void makeContentType(String path){
        String extension = path.substring(path.lastIndexOf(".")+1);
        Map<String, String> contentTypes = new HashMap<>();
        contentTypes.put("js", "text/javascript");
        contentTypes.put("css", "text/css");
        contentTypes.put("png", "image/png");
        contentTypes.put("ico", "image/x-icon"); // TODO: 더 알아봐야 함
        // font
        contentTypes.put("eot", "application/vnd.ms-fontobject");
        contentTypes.put("svg", "image/svg+xml");
        contentTypes.put("ttf", "application/x-font-ttf");
        contentTypes.put("woff", "application/font-woff");
        contentTypes.put("woff2", "font/woff2");

        for (String contentType : contentTypes.keySet()) {
            if (extension.equals(contentType)){ // url의 확장자가 static 폴더 안에 존재하는 확장자면
                this.pathPrefix = "/static";
                this.contentType = contentTypes.get(extension);
                return;
            }
        }
        this.pathPrefix = "/templates"; // 조건문을 통과했다면, templates 폴더 안에 있는 "html 파일"이다.
        this.contentType = "text/html";
    }

    public String getRequestHeader(){
        return requestHeader.toString();
    }

    public void getRequest(){
        for (String key: request.keySet()){
            logger.debug("{}: {}", key, request.get(key));
        }
    }

    public String getPath(){
        return request.get("Path");
    }

    public String getContentType(){
        return contentType;
    }

    public String getPathPrefix(){
        return pathPrefix;
    }
}
