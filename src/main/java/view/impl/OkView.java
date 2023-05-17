package view.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import view.View;

public class OkView implements View {

	@Override
	public byte[] render(String viewPath) {
		try {
			return Files.readAllBytes(new File(viewPath).toPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
