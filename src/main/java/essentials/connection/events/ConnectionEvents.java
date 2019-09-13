package essentials.connection.events;

public enum ConnectionEvents {
	UNKNOWN;

	public static ConnectionEvents getEnum(String name) {
		try {
			ConnectionEvents events = ConnectionEvents.valueOf(name);
			if (events != null)
				return events;
		} catch (IllegalArgumentException e) {
		}

		return ConnectionEvents.UNKNOWN;
	}
}
