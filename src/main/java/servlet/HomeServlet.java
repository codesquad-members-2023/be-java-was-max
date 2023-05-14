package servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class HomeServlet extends HttpServlet {

	public String home(HttpRequest httpRequest, HttpResponse httpResponse) {
		return "index.html";
	}
}
