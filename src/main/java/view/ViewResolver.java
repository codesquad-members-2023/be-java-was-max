package view;

import http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);
    private final String ABSOLUTE_PATH = Paths.get("").toAbsolutePath().toString();
    private final String ROOT_PATH = ABSOLUTE_PATH + "/";
    private final String RESOURCES_PATH = ROOT_PATH + "src/main/resources";
    private final String STATIC_PATH = RESOURCES_PATH + "/static";
    private final String TEMPLATES_PATH = RESOURCES_PATH + "/templates";

    // TODO: redirect 처리 추가 -> location header에 추가
    // TODO: RequestHandler 클래스의 response200Header 메서드 다루기
    // 순서 고민: user/create를 하면, user를 생성하고 해당하는 html을 찾아서 반환하는게 맞는가?(반환할 수 있는 html 파일이 없다면?)

    public String run(String viewPath){
        return findTotalPath(viewPath);
    }

    private String findTotalPath(String viewPath){
        // static 폴더 부터 찾고, 없으면 templates 폴더 에서 찾기.
        // 둘 다 없으면 에러 발생
        if (isFileExist(STATIC_PATH + viewPath)) {
            return STATIC_PATH + viewPath;
        }
        if (isFileExist(TEMPLATES_PATH + viewPath)){
            return TEMPLATES_PATH + viewPath;
        }
        throw new RuntimeException("파일이 존재하지 않습니다. Path: " + viewPath);
//        throw new FileNotFoundException("파일이 존재하지 않습니다. Path: " + viewPath);
    }

    private boolean isFileExist(String path){
        logger.debug("path: {}", path);
        return new File(path).exists();
    }
}
