package templateengine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import servlet.Model;

public class DynamicViewUtils {

	protected static final String HEADER_PATH = "src/main/resources/templates/fragments/header.html";
	protected static final String NAVBAR_PATH = "src/main/resources/templates/fragments/navbar.html";
	private static String navbarHtml;
	private static String headerHtml;

	static {
		try {
			navbarHtml = new String(Files.readAllBytes(new File(NAVBAR_PATH).toPath()));
			headerHtml = new String(Files.readAllBytes(new File(HEADER_PATH).toPath()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * html의 placeholder를 content의 내용으로 바꾸는 메서드이다.
	 * @param html
	 * @param placeholder
	 * @param content
	 */
	protected static void replacePlaceholderWithContent(StringBuilder html, String placeholder, String content) {
		int index = html.indexOf(placeholder);
		html.replace(index, index + placeholder.length(), content);
	}

	/**
	 * tag만 지우는 메서드이다.
	 * 즉 {{#~~}} ~~ {{/~~}} 에서 태그안 내용은 삭제하지않고 태그만 지운다.
	 * @param stringBuilder
	 * @param startTag
	 * @param endTag
	 */
	protected static void removeTags(StringBuilder stringBuilder, String startTag, String endTag) {
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
	protected static void removeSection(StringBuilder stringBuilder, String startTag, String endTag) {
		int start = stringBuilder.indexOf(startTag);
		int end = stringBuilder.indexOf(endTag, start) + endTag.length();
		stringBuilder.delete(start, end);
	}

	protected static void handleHeader(StringBuilder html, String placeHolder) {
		replacePlaceholderWithContent(html, placeHolder, headerHtml);
	}

	/**
	 * navbar 를 render하는 이유는 로그인 상태 유무에 따라 navbar의 내용이 다르기 때문이다.
	 * @param html
	 * @param placeHolder
	 * @param model
	 */
	protected static void handleNavbar(StringBuilder html, String placeHolder, Model model) {
		DynamicViewRenderer dynamicViewRenderer = new DynamicViewRenderer();
		dynamicViewRenderer.render(new StringBuilder(navbarHtml), model);
		replacePlaceholderWithContent(html, placeHolder, navbarHtml);
	}
}
