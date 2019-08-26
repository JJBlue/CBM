package essentials.player;

import java.time.LocalDateTime;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

	@EventHandler
	private void login(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);
		playerConfig.set(PlayerConfigKey.loginTime, LocalDateTime.now());
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(player);
		playerConfig.set(PlayerConfigKey.logoutTime, LocalDateTime.now());
		
		PlayerManager.unload(event.getPlayer().getUniqueId());
	}
}
