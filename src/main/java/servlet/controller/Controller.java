package servlet.controller;

import webserver.util.HttpResponseUtils;

import java.util.Map;

public interface Controller {

    String process(Map<String, String> parameters, HttpResponseUtils httpResponse);
}
