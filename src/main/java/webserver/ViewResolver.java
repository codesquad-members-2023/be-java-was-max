package webserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewResolver {

    private ViewResolver() {
    }

    public static HttpResponse resolve(String result) throws IOException {
        byte[] body;
        if (result.startsWith("/static")) {
            Path path = Paths.get("src/main/resources" + result);
            body = Files.readAllBytes(path);
        } else if (result.startsWith("/")) {
            Path path = Paths.get("src/main/resources/templates" + result);
            body = Files.readAllBytes(path);
        } else {
            body = result.getBytes();
        }
        return new HttpResponse(HttpMethod.OK, body);
    }
}
