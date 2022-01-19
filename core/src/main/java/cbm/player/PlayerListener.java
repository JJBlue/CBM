package cbm.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDateTime;

public class PlayerListener implements Listener {

	@EventHandler
	private void login(PlayerJoinEvent e) {
		join(e.getPlayer());
	}

	@EventHandler
	public void quit(PlayerQuitEvent event) {
		quit(event.getPlayer());
	}

	@EventHandler
	public void death(PlayerDeathEvent event) {
		PlayerConfig playerConfig = PlayerManager.getConfig(event.getEntity());
		playerConfig.set("deathTime", LocalDateTime.now());
		playerConfig.set(PlayerConfigKey.deathLocation, event.getEntity().getLocation());
	}
	
	public static void join(Player player) {
		PlayerConfig playerConfig = PlayerManager.getConfig(player);
		playerConfig.set(PlayerConfigKey.loginTime, LocalDateTime.now());
	}

	public static void quit(Player player) {
		PlayerConfig playerConfig = PlayerManager.getConfig(player);

		LocalDateTime logoutTime = LocalDateTime.now();
		playerConfig.set(PlayerConfigKey.logoutTime, logoutTime);

		LocalDateTime loginTime = playerConfig.getLocalDateTime(PlayerConfigKey.loginTime);
		
		if (loginTime != null) {
			CountTime countTime = new CountTime(playerConfig.getString(PlayerConfigKey.playTime));
			countTime.add(loginTime, logoutTime);

			playerConfig.set(PlayerConfigKey.playTime, countTime.toString());
		}
		
		playerConfig.set(PlayerConfigKey.logoutLocation, player.getLocation());
		
		PlayerManager.unload(player.getUniqueId());
	}
}
