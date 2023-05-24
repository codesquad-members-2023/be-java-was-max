package session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Session {
	private UUID uuid;
	private static Map<UUID, Map<String, Object>> httpSession = new ConcurrentHashMap<>();
	private Map<String, Object> sessionData;

	public Session() {
		this.sessionData = new HashMap<>();
	}

	public void createSession() {
		this.uuid = UUID.randomUUID();
		httpSession.put(uuid, sessionData);
	}

	/**
	 * uuid를 key값으로한 httpSession을 Value로 하는 httpSession에 해당 UUID와 sessionData를 저장후
	 * 파라미터로 받은 key와 value를  sessionData에 저장한다.
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, Object value) {
		Map<String, Object> sessionData = httpSession.get(uuid);
		sessionData.put(key, value);
	}

	public void setUUID(String uuid) {
		this.uuid = UUID.fromString(uuid);
	}

	public UUID getUUID() {
		return uuid;
	}

	public void invalidate() {
		if (uuid != null) {
			httpSession.remove(uuid);
		}
	}

	/**
	 * uuid에 대한 session 이 httpSession에 존재하는지 확인한다.
	 * @return session이 존재하면 true, 존재하지 않으면 false를 반환한다.
	 */
	public boolean isExist() {
		return Optional.ofNullable(uuid)
			.map(uuid -> httpSession.get(uuid) != null)
			.orElse(false);
	}
}
