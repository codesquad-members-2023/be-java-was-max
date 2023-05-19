package servlet;

import db.Database;
import http.HttpUtils;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.util.Map;

public class UserLoginServlet {

	public String login(final HttpRequest httpRequest, final HttpResponse httpResponse) {
		Map<String, String> loginInfo = HttpUtils.parseQueryString(httpRequest.getBody());

		String userId = loginInfo.get("userId");
		String password = loginInfo.get("password");

		User user = Database.findUserById(userId);
		if (user == null || !user.isSamePassword(password)) {
			return "user/login_failed.html";
		}

		return "redirect:/";
	}
}
