package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class View {

	private static final String BASE_PATH = "src/main/resources";
	private static final String WELCOME_PAGE = "/templates/index.html";
	private static Map<String, String> folderMappingMap;
	private final byte[] body;

	public View(String view) {
		folderMappingMap = Map.of(
			"html", "/templates",
			"ico", "/static"
		);
		this.body = readResource(view);
	}

	private String extractFileExtensionFromView(String view) {
		return view.substring(view.lastIndexOf(".") + 1);
	}

	public byte[] readResource(String view) {
		String type = extractFileExtensionFromView(view);

		if (view.contains("redirect")) {
			return new byte[0];
		}

		String viewPath = resolveViewPath(view, type);
		try {
			return Files.readAllBytes(new File(viewPath).toPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String resolveViewPath(String view, String type) {
		if (view.equals("/")) {
			return BASE_PATH + WELCOME_PAGE;
		}
		String folder = folderMappingMap.getOrDefault(type, "");
		return BASE_PATH + folder + view;
	}

	public byte[] getBody() {
		return body;
	}
}
