package interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import request.HttpRequest;
import session.Session;

public class LoginCheckInterceptor {

	private static final List<String> WHITESPACE_URLS = new ArrayList<>(
		Arrays.asList("/user/index.html", "/user/logIn.html", "/user/form.html", "/", "/user/SignIn", "/user/create"));

	public String preHandle(HttpRequest request) {
		Session session = request.getSession();
		String requestURL = request.getURL();

		boolean isWhiteUrl = WHITESPACE_URLS.contains(requestURL);
		boolean isStaticResource = requestURL.contains("static");

		if (!isWhiteUrl && !isStaticResource && !session.isExist()) {
			return "redirect:/user/login.html";
		}
		return null;
	}
}
