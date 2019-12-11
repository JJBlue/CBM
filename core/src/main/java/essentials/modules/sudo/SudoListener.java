package essentials.modules.sudo;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class SudoListener implements Listener {
	@EventHandler
	public void command(PlayerCommandPreprocessEvent event) {
		SudoManager.removePlayer(event.getPlayer());
	}
}
