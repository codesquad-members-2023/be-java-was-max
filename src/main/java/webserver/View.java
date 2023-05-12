package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class View {

	private static final String BASE_PATH = "src/main/resources";
	private static final String WELCOME_PAGE = "/templates/index.html";
	private static Map<String, String> folderMappingMap;

	public View() {
		folderMappingMap = Map.of(
			"text/html", "/templates",
			"image/x-icon", "/static"
		);
	}

	public byte[] readResource(String view, String contentType) {
		String viewPath = resolveViewPath(view, contentType);
		try {
			return Files.readAllBytes(new File(viewPath).toPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String resolveViewPath(String viewPath, String contentType) {
		if (viewPath.equals("/")) {
			return BASE_PATH + WELCOME_PAGE;
		}
		String folder = folderMappingMap.getOrDefault(contentType, "");
		return BASE_PATH + folder + viewPath;
	}
}
