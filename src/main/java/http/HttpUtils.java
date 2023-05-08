package http;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class HttpUtils {

	private HttpUtils() {
	}

	public static Map<String, List<String>> parseHeader(final String headerLine) {
		String[] tokens = headerLine.split(": ");
		String name = tokens[0];
		List<String> values = Arrays.stream(tokens[1].split(", "))
			.collect(Collectors.toUnmodifiableList());

		return Map.of(name, values);
	}
}
