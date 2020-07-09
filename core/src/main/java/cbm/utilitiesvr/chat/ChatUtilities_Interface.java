package cbm.utilitiesvr.chat;

import org.bukkit.entity.Player;

public interface ChatUtilities_Interface {
	public void sendMessage(Player player, String json, ChatMessageType type);
}
