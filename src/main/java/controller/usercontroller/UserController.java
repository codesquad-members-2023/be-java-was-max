package controller.usercontroller;

import controller.Controller;
import webserver.request.HttpRequest;

public class UserController implements Controller {

	@Override
	public String process(HttpRequest request) {
		return request.getURL();
	}
}
