package servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ViewResolver;

import java.io.IOException;

public class DispatcherServlet {

	private static final ViewResolver VIEW_RESOLVER = new ViewResolver();

	public void dispatch(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
		String path = httpRequest.getUri().getPath();
		String viewName = path;

		if (path.equals("/")) {
			viewName = new HomeServlet().home(httpRequest, httpResponse);
		}
		if (path.equals("/user/create")) {
			viewName = new UserJoinServlet().join(httpRequest, httpResponse);
		}

		VIEW_RESOLVER.resolve(viewName, httpResponse);
	}
}
