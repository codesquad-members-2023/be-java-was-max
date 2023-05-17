package Controller.usercontroller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Controller.Controller;
import db.UserRepository;
import model.User;
import webserver.RequestHandler;
import webserver.request.HttpRequest;

public class UserSignInController implements Controller {

	private UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	public UserSignInController() {
		this.userRepository = new UserRepository();
	}

	@Override
	public String process(HttpRequest request) {
		Map<String, String> queryParams = request.getParameters();
		User user = userRepository.findUserById(queryParams.get("userId"));
		logger.debug("savedUser : {}", user);
		return "redirect:/";
	}
}
