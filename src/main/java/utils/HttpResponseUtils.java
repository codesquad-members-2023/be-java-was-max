package utils;

public class HttpResponseUtils {

	private HttpResponseUtils() {
	}

	public static String extractFileExtensionFromView(String view) {
		return view.substring(view.lastIndexOf(".") + 1);
	}
}
