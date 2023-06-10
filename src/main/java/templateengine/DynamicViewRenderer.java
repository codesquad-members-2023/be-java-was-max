package templateengine;

import static templateengine.DynamicViewUtils.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import servlet.Model;

/**
 * 템플릿 엔진 설명
 * 모든 태그는 {{}} 2개의 중괄호 안에서 이루어집니다.
 * 예를 들어 {{>header}}와 같이 태그가 설정되어 있다면, 해당 태그를 모델에서 찾아와 대치합니다."
 * insert_tag 를 사용하는게 아닌 Loop, Conditional tag를 사용한다면 항상 end tag를 통해 해당 태그 적용범위의 끝을 알려줘야합니다.
 */
public class DynamicViewRenderer {

	private static final String LOOP_START_TAG = "$";
	private static final String CONDITIONAL_TRUE_TAG = "#";
	private static final String CONDITIONAL_FALSE_TAG = "^";
	private static final String END_TAG = "/";
	private static final String INSERT_TAG = ">";
	private static final Pattern pattern = Pattern.compile("\\{\\{([#$^/>])([^{}]+)\\}\\}");

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
			// 변경된 html의 시작부터 다시 정규표현식에 match되는 부분을 찾는다.
			matcher = pattern.matcher(html);
		}
	}

	/**
	 * 조건문을 처리할수 있는 메서드이다.
	 * removeSection은 태그 + 태그의 내용을 모두 지우며
	 * removeTags는 태그만 지우는 메서드이다. (태그안 내용은 지우지 않는다)
	 * @param html
	 * @param key
	 * @param model
	 */
	private void handleConditional(StringBuilder html, String key, Model model) {
		String truePattern = "{{" + CONDITIONAL_TRUE_TAG + key + "}}";
		String falsePattern = "{{" + CONDITIONAL_FALSE_TAG + key + "}}";
		String endPattern = "{{" + END_TAG + key + "}}";
		if (model.getBoolTypeValue(key)) {
			removeSection(html, falsePattern, endPattern);
			removeTags(html, truePattern, endPattern);
		} else {
			removeSection(html, truePattern, endPattern);
			removeTags(html, falsePattern, endPattern);
		}
	}

	/**
	 * 루프문을 처리하는 메서드입니다.
	 * 복잡하지만 생각보다 복잡하지 않습니다.
	 * @param html
	 * @param key
	 * @param model
	 */
	private void handleLoop(StringBuilder html, String key, Model model) {
		List<Object> list = model.getListTypeValue(key);  // key = articles
		String loopStartPattern = "{{" + LOOP_START_TAG + key + "}}";
		String loopEndPattern = "{{" + END_TAG + key + "}}";
		int start = html.indexOf(loopStartPattern);
		int end = html.indexOf(loopEndPattern, start) + loopEndPattern.length();

		// 반복해야 할 html
		StringBuilder loopHtmlBlock = new StringBuilder(
			html.substring(start + loopStartPattern.length(), end - loopEndPattern.length()));

		// loop 문을 통해 반복되는 모든 html들을 저장할 객체이다.
		// 이렇게 한 이유는 루프마다 insert 문을 실행시키는것 보다 Stringbuilder에 모든 반복 내용을 저장후 insert문을 마지막에 한번만 실행하기 위해서다.
		StringBuilder replacedHtmls = new StringBuilder();

		for (Object obj : list) {
			Matcher matcher = pattern.matcher(loopHtmlBlock);
			StringBuilder repeatedHtml = new StringBuilder(loopHtmlBlock);
			while (matcher.find()) {
				String valueKey = matcher.group(2).trim();// session
				try {
					//obj의 클래스를 가져오고, valueKey필드를 가져옵니다.
					Field field = obj.getClass().getDeclaredField(valueKey);
					//private 접근제어자에 접근할수있도록 설정해줍니다.
					field.setAccessible(true);
					String value = String.valueOf(field.get(obj));
					replacePlaceholderWithContent(repeatedHtml, "{{>" + valueKey + "}}", value);
				} catch (NoSuchFieldException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				matcher = pattern.matcher(repeatedHtml);
			}

			replacedHtmls.append(repeatedHtml);
		}

		// 최종적으로 만들어진 데이터를 삽입 및 기존에 있던 반복문을 위한 html을 삭제한다.
		html.insert(start, replacedHtmls);
		html.delete(start + replacedHtmls.length(), end + replacedHtmls.length());
	}

	/**
	 * 삽입 태그를 삭제후 실제 데이터로 바꾸는 메서드이다.
	 * @param html
	 * @param key
	 * @param model
	 */
	private void handleInsertion(StringBuilder html, String key, Model model) {
		String placeHolder = "{{" + INSERT_TAG + key + "}}";

		if ("header".equals(key)) {
			handleHeader(html, placeHolder);
		} else if ("navbar".equals(key)) {
			handleNavbar(html, placeHolder, model);
		} else {
			replacePlaceholderWithContent(html, placeHolder, model.getStringTypeValue(key));
		}
	}

}
