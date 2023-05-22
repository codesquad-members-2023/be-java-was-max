package servlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import controller.Controller;
import servlet.mapper.ControllerMapper;
import servlet.mapper.HandlerMapper;
import servlet.view.View;
import servlet.viewResolver.ViewResolver;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseParams;

public class DispatcherServlet {

	private Controller controller;
	private View view;
	private ViewResolver viewResolver;
	private ViewFactory viewFactory;
	private final ControllerMapper controllerMapper;
	private final HandlerMapper handlerMapper;
	private String viewName;

	public DispatcherServlet() {
		this.viewFactory = new ViewFactory();
		this.controllerMapper = new ControllerMapper();
		this.handlerMapper = new HandlerMapper();
	}

	public HttpResponse doDispatch(HttpRequest request) {
		controller = controllerMapper.mapController(request);

		Method handlerMethod = handlerMapper.mapHandler(controller, request);

		try {
			viewName = (String)handlerMethod.invoke(controller, request);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}

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
