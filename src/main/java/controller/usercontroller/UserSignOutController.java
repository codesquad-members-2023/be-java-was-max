package controller.usercontroller;

import annotation.MethodType;
import annotation.RequestMapping;
import controller.Controller;
import request.HttpRequest;
import session.Session;

@RequestMapping("/user/sign-out")
public class UserSignOutController implements Controller {

	@Override
	@MethodType("POST")
	public String process(HttpRequest request) {
		Session session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
}
