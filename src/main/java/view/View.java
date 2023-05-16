package view;

import java.util.Map;

public abstract class View {

	private static final String BASE_PATH = "src/main/resources";
	private static final String WELCOME_PAGE = "/templates/index.html";
	private static Map<String, String> folderMappingMap;
	private byte[] body;

	public View() {
		folderMappingMap = Map.of(
			"html", "/templates",
			"ico", "/static"
		);
	}

	protected String extractFileExtensionFromView(String view) {
		return view.substring(view.lastIndexOf(".") + 1);
	}

	protected String resolveViewPath(String view, String type) {
		if (view.equals("/")) {
			return BASE_PATH + WELCOME_PAGE;
		}
		String folder = folderMappingMap.getOrDefault(type, "");
		return BASE_PATH + folder + view;
	}

	protected abstract byte[] render(String view);

	public byte[] getBody() {
		return body;
	}

	protected void setBody(byte[] body) {
		this.body = body;
	}
}
