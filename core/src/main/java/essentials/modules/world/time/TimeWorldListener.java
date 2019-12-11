package essentials.modules.world.time;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TimeWorldListener implements Listener {
	@EventHandler
	public void teleport(PlayerTeleportEvent event) {
		if (event.getFrom().getWorld() == event.getTo().getWorld()) return;

		TimeWorldManager.removeBossBarPlayer(event.getFrom().getWorld(), event.getPlayer());
		TimeWorldManager.addBossBarPlayer(event.getTo().getWorld(), event.getPlayer());
	}

	@EventHandler
	public void join(PlayerJoinEvent event) {
		TimeWorldManager.addBossBarPlayer(event.getPlayer().getWorld(), event.getPlayer());
	}
}
