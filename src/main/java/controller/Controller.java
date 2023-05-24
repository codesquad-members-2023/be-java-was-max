package controller;

import request.HttpRequest;

public interface Controller {
	String process(HttpRequest request);
}
