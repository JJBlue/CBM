package essentials.modules.move;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import essentials.language.LanguageConfig;
import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import essentials.utilities.PlayerUtilities;

public class afk implements Listener {
	
	public static void change(Player player) {
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		if(player == null) return;
		if(config.containsLoadedKey("afk")) {
			setAfk(player, !config.getBoolean("afk"));
			return;
		}
		setAfk(player, true);
	}
	
	public static void setAfk(Player player, boolean value) {
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(player);
		playerConfig.setTmp("afk", value);
		
		if(!value)
			Bukkit.broadcastMessage(LanguageConfig.getString("afk.noLongerAfk", PlayerUtilities.getName(player)));
		else
			Bukkit.broadcastMessage(LanguageConfig.getString("afk.isNowAfk", PlayerUtilities.getName(player)));
	}
	
	public static boolean isAfk(Player player) {
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		if(config.containsLoadedKey("afk"))
			return config.getBoolean("afk");
		return false;
	}
	
	@EventHandler
	private void Move(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);
		if (!playerConfig.containsLoadedKey("afk") || !playerConfig.getBoolean("afk"))
			return;

//		p.setCollidable(false);
		
		Location to = event.getTo();
		Location from = event.getFrom();

		if (from.getX() != to.getX() || from.getZ() != to.getZ())
			setAfk(p, false);
	}
	
	@EventHandler
	private void chat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		if (!config.containsLoadedKey("afk") || !config.getBoolean("afk"))
			return;
		
		setAfk(player, false);
	}
	
	@EventHandler
	private void interact(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		if (!config.containsLoadedKey("afk") || !config.getBoolean("afk"))
			return;
		
		setAfk(player, false);
	}
}
