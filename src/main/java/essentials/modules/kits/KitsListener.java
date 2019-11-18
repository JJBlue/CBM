package essentials.modules.kits;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import essentials.modules.kits.player.KitPlayerManager;

public class KitsListener implements Listener {
	@EventHandler
	public void quit(PlayerQuitEvent event) {
		KitPlayerManager.unload(event.getPlayer().getUniqueId());
	}
}
