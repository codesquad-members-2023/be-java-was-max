package webserver.frontcontroller.old;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import util.FileUtils;
import webserver.handler.StaticResourceHandler;

public class ViewResolver {

    public String prefix;
    public String suffix;

    public ViewResolver(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getView(String viewName) {
        return prefix + viewName + suffix;
    }

    public void render(String viewPath, HttpRequest request, final HttpResponse response) throws IOException {
        StaticResourceHandler staticResourceHandler = new StaticResourceHandler();
        Optional<File> optionalFile = FileUtils.readFile(viewPath);
        if (optionalFile.isPresent()) {
            staticResourceHandler.process(optionalFile.get(), request, response);
        }
    }
}
