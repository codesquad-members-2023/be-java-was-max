package servlet;

import java.util.Map;

import Controller.Controller;
import Controller.usercontroller.UserController;
import Controller.usercontroller.UserSignInController;
import Controller.usercontroller.UserSignUpController;
import view.View;
import view.impl.OkView;
import view.impl.RedirectView;
import viewResolver.ViewResolver;
import viewResolver.impl.OkViewResolver;
import viewResolver.impl.RedirectViewResolver;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class DispatcherServlet {

	private final Map<String, Controller> controllerMap;
	private Controller controller;
	private View view;
	private ViewResolver viewResolver;

	public DispatcherServlet() {
		controllerMap = Map.of("/user/create", new UserSignUpController(), "/user/signIn", new UserSignInController());
	}

	public HttpResponse doDispatch(HttpRequest request) {
		controller = controllerMap.getOrDefault(request.getURL(), new UserController());

		String viewName = controller.process(request);

		initViewAndViewResolver(viewName);

		String viewPath = viewResolver.viewResolver(viewName);
		byte[] body = view.render(viewPath);

		return new HttpResponse(viewName, body, request.getSession());
	}

	private void initViewAndViewResolver(String viewName) {
		if (viewName.startsWith("redirect")) {
			view = new RedirectView();
			viewResolver = new RedirectViewResolver();
			return;
		}
		view = new OkView();
		viewResolver = new OkViewResolver();
	}

}
