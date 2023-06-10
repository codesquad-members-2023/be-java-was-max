package controller.usercontroller;

import java.util.Map;

import annotation.MethodType;
import annotation.RequestMapping;
import controller.Controller;
import db.UserRepository;
import model.User;
import request.HttpRequest;

@RequestMapping("/user/create")
public class UserSignUpController implements Controller {
	private final UserRepository userRepository;

	public UserSignUpController() {
		userRepository = new UserRepository();
	}

	@Override
	@MethodType("POST")
	public String process(HttpRequest request) {
		Map<String, String> queryParams = request.getParameters();
		User user = new User(queryParams.get("userId"), queryParams.get("password"), queryParams.get("name"),
			queryParams.get("email"));
		userRepository.addUser(user);
		return "redirect:/";
	}
}
