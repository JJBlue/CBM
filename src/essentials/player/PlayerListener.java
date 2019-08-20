package essentials.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void quit(PlayerQuitEvent event) {
		PlayerManager.unload(event.getPlayer().getUniqueId());
	}
}
