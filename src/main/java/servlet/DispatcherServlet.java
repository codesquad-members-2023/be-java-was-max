package servlet;

import java.util.HashMap;
import java.util.Map;

import Controller.Controller;
import Controller.usercontroller.UserSignInController;
import Controller.usercontroller.UserSignUpController;
import view.View;
import webserver.request.HttpRequest;

public class DispatcherServlet {

	private Map<String, Controller> controllerMap = new HashMap<>();

	public DispatcherServlet() {
		controllerMap.put("/user/create", new UserSignUpController());
		controllerMap.put("/user/signIn", new UserSignInController());
	}

	public View doDispatch(HttpRequest request) {
		Controller controller = controllerMap.get(request.getURL());

		ModelAndView modelAndView = controller.process(request.getParameters());
		String viewName = userController.requestMapper(request);

		View myView = initView(viewName);

		return myView;
	}

}
