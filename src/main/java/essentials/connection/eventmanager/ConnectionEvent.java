package essentials.connection.eventmanager;

public class ConnectionEvent {
	public int inOrOut = -1; //0 = in; 1 = Out
	public boolean cancel;

	public Object event;

	public ConnectionEvent(Object object) {
		if (object.getClass().getName().contains("PlayOut"))
			inOrOut = 1;
		else if (object.getClass().getName().contains("PlayIn"))
			inOrOut = 0;

		event = object;
	}

	public boolean isPacketSendIn() {
		return inOrOut == 0;
	}

	public boolean isPacketSendOut() {
		return inOrOut == 1;
	}

	public boolean isCannceled() {
		return cancel;
	}
}
