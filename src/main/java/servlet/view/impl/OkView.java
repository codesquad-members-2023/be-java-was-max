package servlet.view.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import servlet.Model;
import servlet.view.View;
import templateengine.DynamicViewRenderer;

public class OkView implements View {

	private static final String HEADER_PATH = "src/main/resources/templates/fragments/header.html";
	private static final String NAVBAR_PATH = "src/main/resources/templates/fragments/navbar.html";
	private final DynamicViewRenderer dynamicViewRenderer;

	public OkView() {
		this.dynamicViewRenderer = new DynamicViewRenderer();
	}

	@Override
	public byte[] render(String viewPath, Model model) {
		try {
			if (viewPath.contains("static")) {
				return Files.readAllBytes(new File(viewPath).toPath());
			}
			StringBuilder html = new StringBuilder(new String(Files.readAllBytes(new File(viewPath).toPath())));
			return renderDynamicContent(html, model);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] renderDynamicContent(StringBuilder html, Model model) throws IOException {
		StringBuilder header = dynamicViewRenderer.renderHeader(HEADER_PATH);
		StringBuilder navbar = dynamicViewRenderer.renderNavbar(NAVBAR_PATH, model);

		dynamicViewRenderer.renderHTML(html, "{{> header}}", header);
		dynamicViewRenderer.renderHTML(html, "{{> navbar}}", navbar);

		return html.toString().getBytes();
	}

}
