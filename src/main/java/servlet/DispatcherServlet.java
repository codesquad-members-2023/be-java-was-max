package servlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import controller.Controller;
import interceptor.LoginCheckInterceptor;
import request.HttpRequest;
import response.HttpResponse;
import response.HttpResponseParams;
import servlet.mapper.ControllerMapper;
import servlet.mapper.HandlerMapper;
import servlet.view.View;
import servlet.viewResolver.ViewResolver;

public class DispatcherServlet {

	private View view;
	private ViewResolver viewResolver;
	private final ControllerMapper controllerMapper;
	private final HandlerMapper handlerMapper;
	private final LoginCheckInterceptor loginCheckInterceptor;

	public DispatcherServlet() {
		this.controllerMapper = new ControllerMapper();
		this.handlerMapper = new HandlerMapper();
		loginCheckInterceptor = new LoginCheckInterceptor();
	}

	/**
	 * DispatcherServlet의 핵심 로직이 동작하는 메서드 이다.
	 * @param request
	 * @return
	 */
	public HttpResponse doDispatch(HttpRequest request) {

		String viewName = loginCheckInterceptor.preHandle(request);
		if (viewName == null) {
			viewName = getViewName(request);
		}

		initViewAndViewResolver(viewName);

		String viewPath = viewResolver.viewResolver(viewName);
		Model model = new Model(request);
		byte[] body = view.render(viewPath, model);

		return new HttpResponse(new HttpResponseParams(viewName, body, request.getSession()));
	}

	/**
	 * request를 처리할수있는 Controller와 handler를 찾은후
	 * request를 처리하기위해 실행해야할 handler를 return한다.
	 * @param request
	 * @return
	 */
	private String getViewName(HttpRequest request) {
		Controller controller = controllerMapper.mapController(request);
		Method handlerMethod = handlerMapper.mapHandler(controller, request);
		return invokeHandlerMethod(controller, request, handlerMethod);
	}

	/**
	 * handlerMethod를 invoke하는 메서드이다.
	 * @param controller
	 * @param request
	 * @param handlerMethod
	 * @return
	 */
	private String invokeHandlerMethod(Controller controller, HttpRequest request, Method handlerMethod) {
		try {
			return (String)handlerMethod.invoke(controller, request);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * viewName을 통해 어떠한 view, viewResolver 인스턴스를 생성할지 결정한다.
	 * @param viewName
	 */
	private void initViewAndViewResolver(String viewName) {
		ViewFactory viewFactory = new ViewFactory();
		view = viewFactory.view(viewName);
		viewResolver = viewFactory.viewResolver(viewName);
	}
}
