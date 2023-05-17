package http;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class HttpHeaders {

	private final TreeMap<String, List<String>> headersMap;

	public HttpHeaders() {
		this.headersMap = new TreeMap<>();
	}

	public void addHeader(final Map<String, List<String>> header) {
		headersMap.putAll(header);
	}

	@Override
	public String toString() {
		return headersMap.entrySet()
				.stream()
				.map(entry -> entry.getKey() + ": " + String.join(", ", entry.getValue()))
				.collect(Collectors.joining("\n"));
	}
}
