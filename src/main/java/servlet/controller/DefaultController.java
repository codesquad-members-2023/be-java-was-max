package servlet.controller;

import webserver.util.HttpRequestUtils;
import webserver.util.HttpResponseUtils;

public class DefaultController implements Controller {
    @Override
    public String process(HttpRequestUtils httpRequest, HttpResponseUtils httpResponse) {
        return httpRequest.getUrl();
    }
}
