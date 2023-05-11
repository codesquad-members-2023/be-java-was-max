package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HttpRequestUtils {

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
