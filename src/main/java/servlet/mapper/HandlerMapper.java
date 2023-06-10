package servlet.mapper;

import java.lang.reflect.Method;

import annotation.MethodType;
import controller.Controller;
import request.HttpRequest;

public class HandlerMapper {

	/**
	 * controller에 존재하는 메서드중 methodType이 request의 methodType과 같은 handler를 찾은후 반환한다.
	 * @param controller
	 * @param request
	 * @return
	 */
	public Method mapHandler(Controller controller, HttpRequest request) {
		//controller에 존재하는 method들을 methods 배열에 저장한다.
		Method[] methods = controller.getClass().getMethods();

		for (Method method : methods) {
			//method에 붙어있는 methodType Annotation의 인스턴스를 가지고온다.
			MethodType methodType = method.getAnnotation(MethodType.class);

			//Annotation 인스턴스에 존재하는 methodType과 request에 존재하는 methodType이 같은것을 찾아 반환한다.
			if (methodType != null && methodType.value().equals(request.getMethod())) {
				return method;
			}
		}
		throw new RuntimeException("No matching handler found for the request: " + request.getURL());
	}
}
