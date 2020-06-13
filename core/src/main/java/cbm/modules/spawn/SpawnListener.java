package cbm.modules.spawn;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpawnListener implements Listener {
	@EventHandler
	public void join(PlayerJoinEvent event) {
		if(!event.getPlayer().hasPlayedBefore() && SpawnConfiguration.getConfiguration().getBoolean("useFirstJoin"))
			SpawnManager.teleportToSpawn(event.getPlayer(), -1, false);
	}
	
	@EventHandler
	public void death(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Location location = player.getBedSpawnLocation();
		
		if(
			SpawnConfiguration.getConfiguration().getBoolean("TeleportOnDeath") && location == null ||
			SpawnConfiguration.getConfiguration().getBoolean("TeleportAlwaysOnDeath")
		) {
			SpawnManager.teleportToSpawn(player, false);
		}
	}
}
