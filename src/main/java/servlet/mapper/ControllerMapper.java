package servlet.mapper;

import static servlet.mapper.ControllerClassLoader.*;

import java.util.ArrayList;
import java.util.List;

import annotation.RequestMapping;
import controller.Controller;
import controller.usercontroller.UserController;
import request.HttpRequest;

public class ControllerMapper {

	private static List<Controller> controllers = new ArrayList<>();

	static {
		loadControllersFromDirectory();
	}

	/**
	 * request의 url을 통해 controller를 매핑해주는 메서드이다.
	 * @param request
	 * @return
	 */
	public Controller mapController(HttpRequest request) {

		for (Controller controller : controllers) {

			//controller에 존재하는 RequestMapping Annotation의 인스턴스를 가져온다.
			RequestMapping requestMapping = controller.getClass().getAnnotation(RequestMapping.class);

			//Annotation 인스턴스에 존재하는 value와 requst의 url이 같다면 해당 controller를 반환한다.
			if (requestMapping != null && requestMapping.value().equals(request.getURL())) {
				return controller;
			}
		}
		/**
		 * 현재는 모든 url에 대해 controller가 mapping 되어있는게 아니기 때문에
		 * 일단은 url을 처리할수 있는 Controller가 없다면 url을 그대로 return해주는
		 * UserController를 return해준다.
		 */
		return new UserController();
	}

	/**
	 * 디렉토리에서 읽은 Controller를 Controllers에 추가하기 위한 메서드이다.
	 * @param controller
	 */
	public static void addController(Controller controller) {
		controllers.add(controller);
	}
}
