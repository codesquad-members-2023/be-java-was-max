package servlet.view.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import servlet.view.View;

public class OkView implements View {

	@Override
	public byte[] render(String viewPath) {
		try {
			if (viewPath.contains("static")) {
				return Files.readAllBytes(new File(viewPath).toPath());
			}
			StringBuilder html = new StringBuilder(new String(Files.readAllBytes(new File(viewPath).toPath())));
			return renderDynamicContent(html);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] renderDynamicContent(StringBuilder html) throws IOException {
		replacePlaceholderWithFileContent(html, "{{> header}}", "src/main/resources/templates/fragments/header.html");
		replacePlaceholderWithFileContent(html, "{{> navbar}}", "src/main/resources/templates/fragments/navbar.html");
		return html.toString().getBytes();
	}

	private void replacePlaceholderWithFileContent(StringBuilder html, String placeholder, String filePath) throws
		IOException {
		String fileContent = new String(Files.readAllBytes(new File(filePath).toPath()));
		int index = html.indexOf(placeholder);
		html.replace(index, index + placeholder.length(), fileContent);
	}
}
