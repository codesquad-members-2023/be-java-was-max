package servlet;

import db.Database;
import http.HttpUtils;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.util.Map;

public class UserJoinServlet {

	public String join(HttpRequest httpRequest, HttpResponse httpResponse) {
		Map<String, String> queryParameter = HttpUtils.parseQueryString(httpRequest.getBody());

		User user = new User(queryParameter.get("userId"), queryParameter.get("password"), queryParameter.get("name"), queryParameter.get("email"));

		Database.addUser(user);

		return "redirect:/";
	}
}
