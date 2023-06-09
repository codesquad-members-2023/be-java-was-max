package utils;

public class HttpResponseUtils {

	private HttpResponseUtils() {
	}

	/**
	 * 파일의 확장자를 추출하는 작업을 하는 메서드 이다.
	 * @param view
	 * @return
	 */
	public static String extractFileExtensionFromView(String view) {
		return view.substring(view.lastIndexOf(".") + 1);
	}
}
