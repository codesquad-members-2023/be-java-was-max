package servlet.mapper;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import annotation.RequestMapping;
import controller.Controller;
import controller.usercontroller.UserController;
import request.HttpRequest;

public class ControllerMapper {

	private final List<Controller> controllers;

	private static final String ROOT_DIR = "out/production/classes/controller";

	//todo classLoader를 사용해 자동으로 controller가 추가되도록 만들자.
	public ControllerMapper() {
		this.controllers = new ArrayList<>();
		loadControllersFromDirectory();
	}

	private void loadControllersFromDirectory() {
		File rootDir = new File(ROOT_DIR);
		try {
			loadControllers(rootDir, "controller");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * .class 파일이 있는 directory의 클래스들을 load해 List에 저장한다.
	 * @param dir
	 * @param packageName
	 * @throws Exception
	 */
	private void loadControllers(File dir, String packageName) throws Exception {
		//directory 경로를 url로 변경한다.
		URL[] urls = new URL[] {dir.toURI().toURL()};

		//url 배열을 받아 url에 있는 클래스를 load한다.
		URLClassLoader urlClassLoader = new URLClassLoader(urls);

		//directory의 모든 파일을 읽는다.
		File[] files = dir.listFiles();

		for (File file : files) {

			if (file.isDirectory()) {
				//파일이 디렉토리일시 재귀적으로 해당 디렉토리 내의 파일들을 다시 읽는다.
				loadControllers(file, packageName + "." + file.getName());

			} else if (file.getName().endsWith(".class")) {
				String className = packageName + '.' + file.getName().replace(".class", "");
				//이름이 className인 클래스를 load한후 클래스 객체를 가져온다.
				Class<?> controllerClass = urlClassLoader.loadClass(className);

				// controllerClass가 Controller interface 또는 하위 클래스인지 확인한다.
				if (Controller.class.isAssignableFrom(controllerClass) && !controllerClass.isInterface()) {
					Constructor<?> constructor = controllerClass.getConstructor();
					Controller controller = (Controller)constructor.newInstance();
					controllers.add(controller);
				}
			}
		}
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
}
