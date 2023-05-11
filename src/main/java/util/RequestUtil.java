package util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class RequestUtil {
	public static String parseUrl(String line) {
		String tokens[] = line.split(" ");
		return tokens[1];
	}

	public static Map<String, String> parseQueryString(String queryString) {
		if (Strings.isNullOrEmpty(queryString)) {
			return Maps.newHashMap();
		}
		String tokens[] = queryString.split("&");
		return Arrays.stream(tokens).map(t -> getKeyValue(t, "="))
			.filter(p -> p != null)
			.collect(Collectors.toMap(p -> p[0], p -> p[1]));
	}

	static String[] getKeyValue(String keyValue, String regex) {
		if (Strings.isNullOrEmpty(keyValue)) {
			return null;
		}
		String tokens[] = keyValue.split(regex);
		if (tokens.length != 2) {
			return null;
		}
		return tokens;
	}
}
