package Controller;

import webserver.request.HttpRequest;

public interface Controller {
	String process(HttpRequest request);
}
