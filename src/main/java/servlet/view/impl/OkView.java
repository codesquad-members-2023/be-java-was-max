package servlet.view.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import servlet.Model;
import servlet.view.View;
import templateengine.DynamicViewRenderer;

public class OkView implements View {

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
		dynamicViewRenderer.render(html, model);
		return html.toString().getBytes();
	}

}
