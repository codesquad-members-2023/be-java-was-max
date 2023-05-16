package view;

import java.util.Map;

public abstract class View {

	private static final String BASE_PATH = "src/main/resources";
	private static final String WELCOME_PAGE = "/templates/index.html";
	private static Map<String, String> folderMappingMap;
	private byte[] body;
	private String viewName;

	public View(String viewName) {
		folderMappingMap = Map.of(
			"html", "/templates",
			"ico", "/static"
		);
		this.viewName = viewName;
	}

	protected String resolveViewPath(String type) {
		if (viewName.equals("/")) {
			return BASE_PATH + WELCOME_PAGE;
		}
		String folder = folderMappingMap.getOrDefault(type, "");
		return BASE_PATH + folder + viewName;
	}

	protected abstract byte[] render(String view);

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
