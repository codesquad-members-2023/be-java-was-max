package session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Session {
	private UUID uuid;
	private boolean invalidate;
	private static Map<UUID, Map<String, String>> UUIDMap;
	private Map<String, String> httpSession;

	public Session() {
		this.UUIDMap = new ConcurrentHashMap<>();
		this.httpSession = new HashMap<>();
	}

	/**
	 * uuid를 key값으로한 httpSession을 Value로 하는 UUIDMap에 해당 UUID와 httpSession을 저장후
	 * 파라미터로 받은 key와 value를  httpSession에 저장한다.
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, String value) {
		httpSession.put(key, value);
		this.uuid = UUID.randomUUID();
		UUIDMap.put(uuid, httpSession);
	}

	/**
	 * uuid를 통해 httpSession을 찾은후
	 * key값을 통해 value를 찾는다.
	 * @param key
	 * @return
	 */
	public String getAttribute(String key) {
		Map<String, String> httpSession = UUIDMap.get(uuid);
		return httpSession.get(key);
	}

	public void setUUID(String uuid) {
		this.uuid = UUID.fromString(uuid);
	}

	public UUID getUUID() {
		return uuid;
	}

	public void invalidate() {
		this.invalidate = true;
	}

	public boolean isInvalidate() {
		return invalidate;
	}
}
