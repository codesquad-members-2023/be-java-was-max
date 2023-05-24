package view;

import http.ContentType;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);
    private final String ABSOLUTE_PATH = Paths.get("").toAbsolutePath().toString();
    private final String ROOT_PATH = ABSOLUTE_PATH + "/";
    private final String RESOURCES_PATH = ROOT_PATH + "src/main/resources";
    private final String STATIC_PATH = RESOURCES_PATH + "/static";
    private final String TEMPLATES_PATH = RESOURCES_PATH + "/templates";
    private final String REDIRECT = "redirect:";

    public void run(String viewPath, HttpResponse httpResponse){
        try {
            Path path = findTotalPath(viewPath.replace(REDIRECT, ""));
            byte[] body = Files.readAllBytes(path);
            setResponse(viewPath, httpResponse, body);
        } catch (NoSuchFileException e) { // findTotalPath()
            logger.error(e.getMessage());
        } catch (IOException e) { // Files.readAllBytes()
            throw new RuntimeException(e);
        }
    }

    private void setResponse(String viewPath, HttpResponse httpResponse, byte[] body){
        httpResponse.setStatusLine(setStatusLine(viewPath)); // statusLine

        httpResponse.setResponseHeaders("Content-Type: " + ContentType.get(viewPath) + ";charset=utf-8\r\n"); // responseHeaders
        httpResponse.setResponseHeaders("Content-Length: " + body.length + "\r\n");
        if (httpResponse.getStatusCode().equals("302")) {  // redirection이면
            httpResponse.setResponseHeaders("Location: " + viewPath.replace(REDIRECT, "") + "\r\n");
        }

        httpResponse.setResponseBody(body); // responseBody
        httpResponse.logResponse(); // log
    }

    private String setStatusLine(String viewPath){
        if (viewPath.startsWith(REDIRECT)) {
            return "HTTP/1.1 302 Found \r\n";
        }
        return "HTTP/1.1 200 OK \r\n";
    }

    private Path findTotalPath(String viewPath) throws NoSuchFileException {
        // static 폴더 부터 찾고, 없으면 templates 폴더 에서 찾기.
        // 둘 다 없으면 에러 발생
        if (isFileExist(STATIC_PATH + viewPath)) {
            return Paths.get(STATIC_PATH + viewPath);
        }
        if (isFileExist(TEMPLATES_PATH + viewPath)){
            return Paths.get(TEMPLATES_PATH + viewPath);
        }

        throw new NoSuchFileException("파일이 존재하지 않습니다. Path: " + viewPath);
    }

    private boolean isFileExist(String path){
        if (Files.exists(Paths.get(path))){
            logger.debug("path: {}", path); //  경로가 존재 할 때만 log 발생
            return true;
        }
        return false;
    }
}
