package servlet;

import Controller.UserController;
import view.View;
import view.impl.OkResponseView;
import view.impl.RedirectResponseView;
import webserver.request.HttpRequest;

public class DispatcherServlet {

	private final UserController userController;

	public DispatcherServlet() {
		userController = new UserController();
	}

	public View doDispatch(HttpRequest request) {
		String viewName = userController.requestMapper(request);

		View myView = initView(viewName);

		return myView;
	}

	private View initView(String viewName) {
		if (viewName.startsWith("redirect")) {
			return new RedirectResponseView(viewName);
		}
		return new OkResponseView(viewName);
	}
}
