package cbm.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import cbm.utilities.chat.ChatUtilities;
import cbm.utilities.permissions.PermissionHelper;
import cbm.utilities.placeholder.PlaceholderFormatter;

public class ColorListener implements Listener {
	@EventHandler
	private void Chat(AsyncPlayerChatEvent e) {
		if(e.getPlayer().isOp() || e.getPlayer().hasPermission(PermissionHelper.getPermission("usePlaceHolders"))) {
			e.setMessage(ChatUtilities.convertToColor(PlaceholderFormatter.setPlaceholders(e.getPlayer(), e.getMessage())));
			return;
		}
		
		e.setMessage(ChatUtilities.convertToColor(e.getMessage()));
	}
}