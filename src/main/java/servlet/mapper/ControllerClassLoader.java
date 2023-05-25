package servlet.mapper;

import static servlet.mapper.ControllerMapper.*;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import controller.Controller;

public class ControllerClassLoader {

	private static final String ROOT_DIR = "out/production/classes/controller";

	public static void loadControllersFromDirectory() {
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
	private static void loadControllers(File dir, String packageName) throws Exception {
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

					//ControllerMapper에 존재하는 Controllers에 디렉토리에서 찾은 controller를 추가한다.
					addController(controller);
				}
			}
		}
	}
}
