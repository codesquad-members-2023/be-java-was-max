package Controller;

import java.util.Map;

import model.User;
import webserver.HttpRequest;

public class UserController {

	public void requestMapper(HttpRequest httpRequest) {
		if (httpRequest.getURL().contains("create")) {
			save(httpRequest.getQueryParams());
		}
	}

	public void save(Map<String, String> queryParams) {
		User user = new User(queryParams.get("userId"), queryParams.get("password"), queryParams.get("name"),
			queryParams.get("email"));
	}
}
