package servlet;

import java.util.Map;

import controller.Controller;
import controller.usercontroller.UserController;
import controller.usercontroller.UserSignInController;
import controller.usercontroller.UserSignUpController;
import servlet.view.View;
import servlet.viewResolver.ViewResolver;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseParams;

public class DispatcherServlet {

	private final Map<String, Controller> controllerMap;
	private Controller controller;
	private View view;
	private ViewResolver viewResolver;
	private ViewFactory viewFactory;

	public DispatcherServlet() {
		controllerMap = Map.of("/user/create", new UserSignUpController(), "/user/signIn", new UserSignInController());
		viewFactory = new ViewFactory();
	}

	public HttpResponse doDispatch(HttpRequest request) {
		controller = controllerMap.getOrDefault(request.getURL(), new UserController());

		String viewName = controller.process(request);

		initViewAndViewResolver(viewName);

		String viewPath = viewResolver.viewResolver(viewName);
		byte[] body = view.render(viewPath);

		return new HttpResponse(new HttpResponseParams(viewName, body, request.getSession()));
	}

	/**
	 * viewName을 통해 어떠한 view, viewResolver 인스턴스를 생성할지 결정한다.
	 * @param viewName
	 */
	private void initViewAndViewResolver(String viewName) {
		view = viewFactory.view(viewName);
		viewResolver = viewFactory.viewResolver(viewName);
	}

}
