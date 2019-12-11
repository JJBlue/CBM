package essentials.modules.timer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TimerListener implements Listener {
	@EventHandler
	public void join(PlayerJoinEvent event) {
		TimerManager.addPlayer(event.getPlayer());
	}
}
