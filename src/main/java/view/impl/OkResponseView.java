package view.impl;

import static utils.HttpResponseUtils.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import view.View;

public class OkResponseView extends View {

	public OkResponseView(String viewName) {
		super(viewName);
		setBody(render(viewName));
	}

	@Override
	public byte[] render(String view) {
		String type = extractFileExtensionFromView(view);

		String viewPath = viewResolver(type);
		try {
			return Files.readAllBytes(new File(viewPath).toPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
