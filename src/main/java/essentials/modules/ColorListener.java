package essentials.modules;

import essentials.utilities.chat.ChatUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ColorListener implements Listener {
	@EventHandler
	private void Chat(AsyncPlayerChatEvent e){
		e.setMessage(ChatUtilities.convertToColor(e.getMessage()));
	}
}