package container;

import container.domain.HttpMethod;
import container.domain.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewResolver {

    public static final String SRC_MAIN_RESOURCES = "src/main/resources";
    public static final String PREFIX = "/";

    private ViewResolver() {
    }

    public static HttpResponse resolve(String result) throws IOException {
        byte[] body;
        if (result.startsWith(PREFIX)) {
            Path path = Paths.get(SRC_MAIN_RESOURCES + result);
            body = Files.readAllBytes(path);
        } else {
            body = result.getBytes();
        }
        return new HttpResponse(HttpMethod.OK, body);
    }
}
