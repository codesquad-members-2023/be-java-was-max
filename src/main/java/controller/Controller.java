package controller;

import webserver.request.HttpRequest;

public interface Controller {
	String process(HttpRequest request);
}
