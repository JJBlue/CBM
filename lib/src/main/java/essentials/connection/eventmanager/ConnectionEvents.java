package essentials.connection.eventmanager;

public enum ConnectionEvents {
	UNKNOWN;

	public static ConnectionEvents getEnum(String name) {
		try {
			return ConnectionEvents.valueOf(name);
		} catch (IllegalArgumentException e) {
			return ConnectionEvents.UNKNOWN;
		}
	}
}
