package servlet.viewResolver.impl;

import static utils.HttpResponseUtils.*;

import java.util.Map;

import servlet.viewResolver.ViewResolver;

public class OkViewResolver implements ViewResolver {

	private static final String BASE_PATH = "src/main/resources";
	private static final String WELCOME_PAGE = "/templates/index.html";
	private final Map<String, String> folderMappingMap;

	public OkViewResolver() {
		folderMappingMap = Map.of(
			"html", "/templates",
			"ico", "/static"
		);
	}

	/**
	 * viewName 을가지고 해당 view의 경로를 찾는 메서드 이다.
	 * @param viewName
	 * @return
	 */
	@Override
	public String viewResolver(String viewName) {
		if (viewName.equals("/")) {
			return BASE_PATH + WELCOME_PAGE;
		}
		String folder = folderMappingMap.getOrDefault(extractFileExtensionFromView(viewName), "");
		return BASE_PATH + folder + viewName;
	}
}
