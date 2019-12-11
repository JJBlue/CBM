package essentials.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import essentials.utilities.chat.ChatUtilities;
import essentials.utilities.permissions.PermissionHelper;
import essentials.utilities.placeholder.PlaceholderFormatter;

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