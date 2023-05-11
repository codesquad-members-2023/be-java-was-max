package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {

	public static Map<String, String> parseQueryString(String queryString) {
		Map<String, String> queryParams = new HashMap<>();
		if (!queryString.equals("")) {
			String[] params = queryString.split("&");
			for (String param : params) {
				String[] keyValue = param.split("=");
				queryParams.put(keyValue[0], keyValue.length > 1 ? keyValue[1] : "");
			}
		}
		return queryParams;
	}

	public static String decodeURL(String URL) {
		try {
			String decodedURL = URLDecoder.decode(URL, "UTF-8");
			return decodedURL;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return URL;
	}
}
