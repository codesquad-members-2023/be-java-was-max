package Controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import webserver.request.HttpRequest;

public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	public String requestMapper(HttpRequest httpRequest) {
		if (httpRequest.getURL().contains("create")) {
			return save(httpRequest.getQueryParams());
		}
		return httpRequest.getURL();
	}

	public String save(Map<String, String> queryParams) {
		User user = new User(queryParams.get("userId"), queryParams.get("password"), queryParams.get("name"),
			queryParams.get("email"));
		logger.debug("user: {}", user);
		return "redirect:/index.html";
	}
}
