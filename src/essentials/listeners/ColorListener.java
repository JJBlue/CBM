package essentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import essentials.utilities.chat.ChatUtilities;

public class ColorListener implements Listener {
	@EventHandler
	private void Chat(AsyncPlayerChatEvent e){
		e.setMessage(ChatUtilities.convertToColor(e.getMessage()));
	}
}