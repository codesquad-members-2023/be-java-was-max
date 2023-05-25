package templateengine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import servlet.Model;

public class DynamicViewRenderer {

	private static final String LOOP_START_TAG = "$";
	private static final String CONDITIONAL_TRUE_TAG = "#";
	private static final String CONDITIONAL_FALSE_TAG = "^";
	private static final String REAL_END_TAG = "/";
	private static final String INSERT_TAG = ">";
	private static final String HEADER_PATH = "src/main/resources/templates/fragments/header.html";
	private static final String NAVBAR_PATH = "src/main/resources/templates/fragments/navbar.html";
	private static final Pattern pattern = Pattern.compile("\\{\\{([#$^/>])([^{}]+)\\}\\}");
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

	public void render(StringBuilder html, Model model) {
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			String tag = matcher.group(1);
			String key = matcher.group(2).trim();

			switch (tag) {
				case CONDITIONAL_TRUE_TAG:
					handleConditional(html, key, model);
					break;
				case LOOP_START_TAG:
					handleLoop(html, key, model);
					break;
				case INSERT_TAG:
					handleInsertion(html, key, model);
					break;
				default:
					break;
			}
			matcher = pattern.matcher(html);
		}
	}

	private StringBuilder handleConditional(StringBuilder html, String key, Model model) {
		String truePattern = "{{" + CONDITIONAL_TRUE_TAG + key + "}}";
		String falsePattern = "{{" + CONDITIONAL_FALSE_TAG + key + "}}";
		String endPattern = "{{" + REAL_END_TAG + key + "}}";
		if (model.getBoolTypeValue(key)) {
			removeSection(html, falsePattern, endPattern);
			removeTags(html, truePattern, endPattern);
		} else {
			removeSection(html, truePattern, endPattern);
			removeTags(html, falsePattern, endPattern);
		}
		return html;
	}

	private void handleLoop(StringBuilder html, String key, Model model) {

	}

	private void handleInsertion(StringBuilder html, String key, Model model) {
		String placeHolder = "{{" + INSERT_TAG + key + "}}";
		if ("header".equals(key)) {
			replacePlaceholderWithContent(html, placeHolder, headerHtml);
		} else if ("navbar".equals(key)) {
			render(new StringBuilder(navbarHtml), model);
			replacePlaceholderWithContent(html, placeHolder, navbarHtml);
		}
	}

	private void replacePlaceholderWithContent(StringBuilder html, String placeholder, String content) {
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
