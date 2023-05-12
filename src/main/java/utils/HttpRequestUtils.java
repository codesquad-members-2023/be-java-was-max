package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestUtils {

	public static Map<String, String> parseQueryString(String queryString) {
		if (queryString.equals("")) {
			return new HashMap<>();
		}
		return Arrays.stream(queryString.split("&"))
			.map(s -> s.split("="))
			.collect(Collectors.toMap(a -> a[0], a -> a.length > 1 ? a[1] : ""));
	}

	public static String decodeURL(String URL) {
		return URLDecoder.decode(URL, StandardCharsets.UTF_8);
	}
}
