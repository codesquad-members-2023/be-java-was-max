package session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Session {
	private UUID uuid;
	private static Map<UUID, Map<String, String>> UUIDMap;
	private Map<String, String> httpSession;

	public Session() {
		this.UUIDMap = new ConcurrentHashMap<>();
		this.httpSession = new HashMap<>();
	}

	/**
	 * uuid를 key값으로한 httpSession Map에 key와 value를 저장한다.
	 * 사실 setAttribute 를 할때마다 UUID를 생성하면 문제가 생기지만
	 * 일단은 넘어간다.
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

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getUUID() {
		return uuid;
	}

}
