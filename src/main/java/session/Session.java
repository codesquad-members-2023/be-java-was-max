package session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {
	private UUID uuid;
	private static Map<UUID, String> sessionMap = new HashMap<>();

	public void addUserToSessionMap(String userId) {
		this.uuid = UUID.randomUUID();
		sessionMap.put(uuid, userId);
	}

	public UUID getUUID() {
		return uuid;
	}

	public String getUserIdByUUID(UUID uuid) {
		return sessionMap.get(uuid);
	}
}
