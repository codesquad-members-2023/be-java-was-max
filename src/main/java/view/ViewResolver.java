package view;

import http.response.HttpResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class ViewResolver {

    private static final String STATIC_PATH = "src/main/resources/static";
    private static final String TEMPLATES_PATH = "src/main/resources/templates";
    public static final String FILE_NOT_FOUND_MESSAGE = "파일이 존재하지 않습니다 : ";

    public void resolve(String name, HttpResponse response) throws IOException {
        if (existsInTemplates(name)) { // templates 우선 탐색
            response.setBody(Files.readAllBytes(new File(TEMPLATES_PATH + name).toPath()));
            return;
        }
        if (existsInStatic(name)) {
            response.setBody(Files.readAllBytes(new File(STATIC_PATH + name).toPath()));
            return;
        }
        throw new FileNotFoundException(FILE_NOT_FOUND_MESSAGE + name);
    }

    private boolean existsInStatic(String name) {
        return new File(STATIC_PATH + name).exists();
    }

    private boolean existsInTemplates(String name) {
        return new File(TEMPLATES_PATH + name).exists();
    }
}
