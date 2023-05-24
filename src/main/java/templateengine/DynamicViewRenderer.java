package templateengine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import servlet.Model;
import session.Session;

public class DynamicViewRenderer {

	private static final String LOGGED_IN_TAG = "{{#session}}";
	private static final String LOGGED_OUT_TAG = "{{^session}}";
	private static final String END_TAG = "{{/session}}";

	public void renderHTML(StringBuilder html, String placeHolder, StringBuilder revisedHtml) {
		replacePlaceholderWithContent(html, placeHolder, revisedHtml.toString());
	}

	public StringBuilder renderHeader(String path) throws IOException {
		StringBuilder navbar = getFileContent(path);
		return navbar;
	}

	public StringBuilder renderNavbar(String path, Model model) throws IOException {
		StringBuilder navbar = getFileContent(path);
		modifyNavbarForSession(navbar, model.getSession());
		return navbar;
	}

	private StringBuilder getFileContent(String filepath) throws IOException {
		return new StringBuilder(new String(Files.readAllBytes(new File(filepath).toPath())));
	}

	private void replacePlaceholderWithContent(StringBuilder html, String placeholder, String content) {
		int index = html.indexOf(placeholder);
		html.replace(index, index + placeholder.length(), content);
	}

	//todo session 외 다른 model 값에 대해서도 동작할수 있도록 리팩토링이 필요하다. 
	private void modifyNavbarForSession(StringBuilder navbar, Session session) {
		if (session.isExist()) {
			removeSection(navbar, LOGGED_OUT_TAG, END_TAG);
			removeTags(navbar, LOGGED_IN_TAG, END_TAG);
		} else {
			removeSection(navbar, LOGGED_IN_TAG, END_TAG);
			removeTags(navbar, LOGGED_OUT_TAG, END_TAG);
		}
	}

	/**
	 * tag만 지우는 메서드이다.
	 * 즉 {{#~~}} ~~ {{/~~}} 에서 태그안 내용은 삭제하지않고 태그만 지운다.
	 * @param stringBuilder
	 * @param startTag
	 * @param endTag
	 */
	private void removeTags(StringBuilder stringBuilder, String startTag, String endTag) {
		stringBuilder.replace(stringBuilder.indexOf(startTag), stringBuilder.indexOf(startTag) + startTag.length(), "");
		stringBuilder.replace(stringBuilder.indexOf(endTag), stringBuilder.indexOf(endTag) + endTag.length(), "");
	}

	/**
	 * section 전체를 지우는 메서드 이다.
	 * 즉 {{#~~}} ~~ {{/~~}} 를 모두 지운다.
	 * @param stringBuilder
	 * @param startTag
	 * @param endTag
	 */
	private void removeSection(StringBuilder stringBuilder, String startTag, String endTag) {
		int start = stringBuilder.indexOf(startTag);
		int end = stringBuilder.indexOf(endTag, start) + endTag.length();
		stringBuilder.delete(start, end);
	}
}
