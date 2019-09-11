package essentials.modules.trade;

import org.bukkit.entity.Player;

public class TradeInformation {
	private long startRequest;
	private Player requestSendFrom;
	
	public TradeInformation(long startRequest, Player requestSendFrom) {
		this.startRequest = startRequest;
		this.requestSendFrom = requestSendFrom;
	}

	public Player getRequestSendFrom() {
		return requestSendFrom;
	}

	public long getStartRequest() {
		return startRequest;
	}

	public void setStartRequest(long startRequest) {
		this.startRequest = startRequest;
	}
}
