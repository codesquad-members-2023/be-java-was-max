package servlet.view.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import servlet.Model;
import servlet.view.View;
import session.Session;

public class OkView implements View {

	private static final String headerPath = "src/main/resources/templates/fragments/header.html";
	private static final String navbarPath = "src/main/resources/templates/fragments/navbar.html";

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
		StringBuilder header = getFileContent(headerPath);
		replacePlaceholderWithContent(html, "{{> header}}", header.toString());

		StringBuilder navbar = getFileContent(navbarPath);
		modifyNavbarForSession(navbar, model.getSession());
		replacePlaceholderWithContent(html, "{{> navbar}}", navbar.toString());

		return html.toString().getBytes();
	}

	private StringBuilder getFileContent(String filepath) throws IOException {
		return new StringBuilder(new String(Files.readAllBytes(new File(filepath).toPath())));
	}

	private void replacePlaceholderWithContent(StringBuilder html, String placeholder, String content) {
		int index = html.indexOf(placeholder);
		html.replace(index, index + placeholder.length(), content);
	}

	private void modifyNavbarForSession(StringBuilder navbar, Session session) {
		String loggedInTag = "{{#session}}";
		String loggedOutTag = "{{^session}}";
		String endTag = "{{/session}}";

		if (session.getUUID() != null) {
			removeSection(navbar, loggedOutTag, endTag);
			removeTags(navbar, loggedInTag, endTag);
		} else {
			removeSection(navbar, loggedInTag, endTag);
			removeTags(navbar, loggedOutTag, endTag);
		}
	}

	private void removeTags(StringBuilder stringBuilder, String startTag, String endTag) {
		stringBuilder.replace(stringBuilder.indexOf(startTag), stringBuilder.indexOf(startTag) + startTag.length(), "");
		stringBuilder.replace(stringBuilder.indexOf(endTag), stringBuilder.indexOf(endTag) + endTag.length(), "");
	}

	private void removeSection(StringBuilder stringBuilder, String startTag, String endTag) {
		int start = stringBuilder.indexOf(startTag);
		int end = stringBuilder.indexOf(endTag, start) + endTag.length();
		stringBuilder.delete(start, end);
	}
}
