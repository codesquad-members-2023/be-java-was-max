package webserver.dispatcher_servlet;

import http.response.HttpServletResponse;
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

    public void render(String viewPath, final HttpServletResponse response) {
        StaticResourceHandler staticResourceHandler = new StaticResourceHandler();
        Optional<File> optionalFile = FileUtils.getFile(viewPath);
        optionalFile.ifPresent(file -> {
            try {
                staticResourceHandler.process(file, response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
