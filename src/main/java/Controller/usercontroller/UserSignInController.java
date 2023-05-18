package Controller.usercontroller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Controller.Controller;
import db.UserRepository;
import model.User;
import session.Session;
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

		String requestId = queryParams.get("userId");
		String requestPassword = queryParams.get("password");

		User user = userRepository.findUserById(requestId);

		if (isSignInFailed(requestPassword, user)) {
			return "redirect:login_failed.html";
		}

		logger.debug("savedUser : {}", user);
		addUserToSessionMap(request, requestId);
		return "redirect:/";
	}

	private void addUserToSessionMap(HttpRequest request, String requestId) {
		Session session = request.getSession();
		session.addUserToSessionMap(requestId);
	}

	private static boolean isSignInFailed(String requestPassword, User user) {
		return user == null || !user.isPasswordValid(requestPassword);
	}
}
