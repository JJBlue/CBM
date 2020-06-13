package cbm.modules.move;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import cbm.language.LanguageConfig;
import cbm.modules.collision.CollisionManager;
import cbm.player.PlayerConfig;
import cbm.player.PlayerManager;
import cbm.utilitiesvr.player.PlayerUtilities;

public class AFK implements Listener {
	
	public static void change(Player player) {
		PlayerConfig config = PlayerManager.getConfig(player);
		if(config.containsLoadedKey("afk")) {
			setAfk(player, !config.getBoolean("afk"));
			return;
		}
		setAfk(player, true);
	}
	
	public static void setAfk(Player player, boolean value) {
		PlayerConfig playerConfig = PlayerManager.getConfig(player);
		playerConfig.setTmp("afk", value);
		
		if(!value) {
			Bukkit.broadcastMessage(LanguageConfig.getString("afk.noLongerAfk", PlayerUtilities.getName(player)));
			CollisionManager.setCollision(player, true);
		} else {
			CollisionManager.setCollision(player, false);
			Bukkit.broadcastMessage(LanguageConfig.getString("afk.isNowAfk", PlayerUtilities.getName(player)));
		}
	}
	
	public static boolean isAfk(Player player) {
		PlayerConfig config = PlayerManager.getConfig(player);
		if(config.containsLoadedKey("afk"))
			return config.getBoolean("afk");
		return false;
	}
	
	@EventHandler
	private void damage(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		
		PlayerConfig playerConfig = PlayerManager.getConfig(player);
		if (!playerConfig.containsLoadedKey("afk") || !playerConfig.getBoolean("afk"))
			return;
		
		event.setCancelled(true);
	}
	
	@EventHandler
	private void damage(EntityDamageByBlockEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		
		PlayerConfig playerConfig = PlayerManager.getConfig(player);
		if (!playerConfig.containsLoadedKey("afk") || !playerConfig.getBoolean("afk"))
			return;
		
		event.setCancelled(true);
	}
	
	@EventHandler
	private void Move(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		
		PlayerConfig playerConfig = PlayerManager.getConfig(p);
		if (!playerConfig.containsLoadedKey("afk") || !playerConfig.getBoolean("afk"))
			return;
		
		setAfk(p, false);
	}
	
	@EventHandler
	private void chat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		PlayerConfig config = PlayerManager.getConfig(player);
		if (!config.containsLoadedKey("afk") || !config.getBoolean("afk"))
			return;
		
		setAfk(player, false);
	}
	
	@EventHandler
	private void interact(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		PlayerConfig config = PlayerManager.getConfig(player);
		if (!config.containsLoadedKey("afk") || !config.getBoolean("afk"))
			return;
		
		setAfk(player, false);
	}
}
