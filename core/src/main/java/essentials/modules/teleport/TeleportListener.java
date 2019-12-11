package essentials.modules.teleport;

import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class TeleportListener implements Listener {
	@EventHandler
	public void teleport(PlayerTeleportEvent event) {
		TeleportCause cause = event.getCause();
		if (!cause.equals(TeleportCause.COMMAND) && !cause.equals(TeleportCause.UNKNOWN) && !cause.equals(TeleportCause.PLUGIN))
			return;

		Player p = event.getPlayer();
		PlayerConfig playerConfig = PlayerManager.getConfig(p);
		playerConfig.set(PlayerConfigKey.tpLocation, event.getFrom());
	}

	@EventHandler
	public void command(PlayerCommandPreprocessEvent event) {
		Player executer = event.getPlayer();
		String message = event.getMessage();

		while (message.startsWith("/"))
			message = message.substring(1);

		if (!message.startsWith("tp") && !message.startsWith("teleport")) return;

		String[] args = message.split(" ");
		
		if(args.length < 2) return;
		Player p1 = Bukkit.getPlayer(args[1]);

		if (p1 != null && p1 != executer) {
			PlayerConfig playerConfig = PlayerManager.getConfig(p1);
			if (playerConfig.getBoolean(PlayerConfigKey.tTp)) {
				event.setCancelled(true);
				executer.sendMessage("Sorry, er hat leider tptoggle aktiv");
			}
		} else {
			Player p2 = Bukkit.getPlayer(args[2]);
			if (p2 == null) return;

			PlayerConfig playerConfig = PlayerManager.getConfig(p2);
			if (playerConfig.getBoolean(PlayerConfigKey.tTp)) {
				event.setCancelled(true);
				executer.sendMessage("Sorry, er hat leider tptoggle aktiv");
			}
		}
	}
	
	@EventHandler
	public void move(PlayerMoveEvent event) {
		if(
			event.getFrom().getBlockX() != event.getTo().getBlockX() ||
			event.getFrom().getBlockY() != event.getTo().getBlockY() ||
			event.getFrom().getBlockZ() != event.getTo().getBlockZ()
		) {
			TeleportManager.removeEntity(event.getPlayer());
		}
	}
}
