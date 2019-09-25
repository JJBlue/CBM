package essentials.modules.spawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpawnListener implements Listener {
	@EventHandler
	public void join(PlayerJoinEvent event) {
		if(!event.getPlayer().hasPlayedBefore())
			SpawnManager.teleportToSpawn(event.getPlayer(), "firstJoin");
	}
}
