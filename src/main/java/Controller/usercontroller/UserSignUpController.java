package Controller.usercontroller;

import java.util.Map;

import Controller.Controller;
import db.UserRepository;
import model.User;
import webserver.request.HttpRequest;

public class UserSignUpController implements Controller {
	private final UserRepository userRepository;

	public UserSignUpController() {
		userRepository = new UserRepository();
	}

	@Override
	public String process(HttpRequest request) {
		Map<String, String> queryParams = request.getParameters();
		User user = new User(queryParams.get("userId"), queryParams.get("password"), queryParams.get("name"),
			queryParams.get("email"));
		userRepository.addUser(user);
		return "redirect:/";
	}
}
