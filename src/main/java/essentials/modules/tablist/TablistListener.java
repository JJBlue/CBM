package essentials.modules.tablist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TablistListener implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if(!Tablist.onJoin) return;
		Tablist.update(event.getPlayer());
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		if(Tablist.onTeleport) {
			Tablist.update(event.getPlayer());
		} else if(Tablist.onWorldChange) {
			if(event.getFrom().getWorld() != event.getTo().getWorld())
				Tablist.update(event.getPlayer());
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if(!Tablist.onDeath) return;
		Tablist.update(event.getEntity());
	}
}
