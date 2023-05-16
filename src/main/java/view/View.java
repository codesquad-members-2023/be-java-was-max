package view;

import java.util.Map;

public abstract class View {

	private static final String BASE_PATH = "src/main/resources";
	private static final String WELCOME_PAGE = "/templates/index.html";
	private final Map<String, String> folderMappingMap;
	private final String viewName;
	private byte[] body;

	public View(String viewName) {
		folderMappingMap = Map.of(
			"html", "/templates",
			"ico", "/static"
		);
		this.viewName = viewName;
	}

	/**
	 * viewName 을가지고 해당 view의 경로를 찾는 메서드 이다.
	 * @param type
	 * @return
	 */
	protected String viewResolver(String type) {
		if (viewName.equals("/")) {
			return BASE_PATH + WELCOME_PAGE;
		}
		String folder = folderMappingMap.getOrDefault(type, "");
		return BASE_PATH + folder + viewName;
	}

	/**
	 * viewResolver에서 찾은 경로를 통해 해당 파일을 읽어온다.
	 * @param viewName
	 * @return
	 */
	protected abstract byte[] render(String viewName);

	public byte[] getBody() {
		return body;
	}

	protected void setBody(byte[] body) {
		this.body = body;
	}

	public String getViewName() {
		return viewName;
	}
}
