package essentials.player;

import java.text.SimpleDateFormat;
import java.util.Date;

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
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		playerConfig.set(PlayerConfigKey.loginTime, df.format(new Date()));
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(player);
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.d)d HH:mm:ss");
		playerConfig.set(PlayerConfigKey.logoutTime, df.format(new Date()));
		
		PlayerManager.unload(event.getPlayer().getUniqueId());
	}
}
