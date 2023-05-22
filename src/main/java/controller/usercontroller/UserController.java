package controller.usercontroller;

import annotation.MethodType;
import controller.Controller;
import webserver.request.HttpRequest;

public class UserController implements Controller {

	@Override
	@MethodType("GET")
	public String process(HttpRequest request) {
		return request.getURL();
	}
}
