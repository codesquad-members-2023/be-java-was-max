package servlet.controller;

import webserver.util.HttpRequestUtils;
import webserver.util.HttpResponseUtils;

public interface Controller {

    String process(HttpRequestUtils httpRequest, HttpResponseUtils httpResponse);
}
