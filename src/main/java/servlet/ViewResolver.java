package servlet;

import servlet.domain.HttpResponseStatus;
import servlet.domain.HttpResponse;

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
        String session = null;
        if (result.contains("sid=")) {
            int index = result.indexOf("sid=");
            session = result.substring(index);
            result = result.substring(0, index-1);
        }

        if (result.startsWith(PREFIX)) {
            Path path = Paths.get(SRC_MAIN_RESOURCES + result);
            body = Files.readAllBytes(path);
        } else {
            body = result.getBytes();
        }
        return session == null ? new HttpResponse(HttpResponseStatus.OK, body) :
                new HttpResponse(HttpResponseStatus.OK, body, session);
    }
}
