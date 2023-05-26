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
    private final ViewRender viewRender = new ViewRender();

    public void run(String viewPath, HttpResponse httpResponse){
        try {
            Path path = findTotalPath(viewPath.replace(REDIRECT, ""));
            byte[] body = Files.readAllBytes(path);
            viewRender.render(viewPath, body, httpResponse, REDIRECT);
        } catch (NoSuchFileException e) { // findTotalPath()
            logger.error(e.getMessage());
        } catch (IOException e) { // Files.readAllBytes()
            throw new RuntimeException(e);
        }
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
