package essentials.modules;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import essentials.language.LanguageConfig;
import essentials.main.Main;
import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;
import essentials.player.PlayersYMLConfig;
import essentials.utilities.StringUtilities;
import essentials.utilities.permissions.PermissionHelper;
import essentials.utilities.placeholder.PlaceholderFormatter;

public class MainListener implements Listener {
	public final static Set<Player> hide = new HashSet<>();

	@EventHandler
	private void Chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);

		if (playerConfig.getBoolean(PlayerConfigKey.tMute))
			e.setCancelled(true);
		
		chat(e);
		convertChat(e);
	}
	
	private void chat(AsyncPlayerChatEvent event) {
		ConfigurationSection section = PlayersYMLConfig.getConfigurationSection("chat");
		if(section == null) return;
		
		Player player = event.getPlayer();
		
		if(!section.getBoolean("enable") && !PermissionHelper.hasPermission(player, "chat.write")) {
			event.setCancelled(true);
			event.setMessage(null);
		} else if(section.getBoolean("timestamp")) {
			String format = section.getString("timestamp-format");
			if(format == null)
				format = "HH:mm";
			
			String time = PlaceholderFormatter.setPlaceholders(player, "%real_time%[" + format + "]");
			event.setFormat("[" + time + "] " + event.getFormat());
		}
	}
	
	private void convertChat(AsyncPlayerChatEvent event) {
		ConfigurationSection section = PlayersYMLConfig.getConfigurationSection("command");
		if(section == null) return;

		Player player = event.getPlayer();
		String text = event.getMessage();
		
		if (section.getBoolean("convert") && (!section.getBoolean("convert-use-permission") || PermissionHelper.hasPermission(player, "command.convert"))) {
			if (text.startsWith("\\7")) {
				event.setMessage(text.replaceFirst("\\", ""));
			} else if (text.startsWith("7")) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> player.performCommand(text.replaceFirst("7", "")));

				LanguageConfig.sendMessage(player, "command.convert");
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	private void onLeave(PlayerQuitEvent event) {
		ConfigurationSection section = PlayersYMLConfig.getConfigurationSection("leave");
		if(section == null) return;
		
		if(section.getBoolean("silent"))
			event.setQuitMessage(null);
		else if(section.getBoolean("messages-enable")) {
			List<String> messages = section.getStringList("messages");
			
			if(!messages.isEmpty()) {
				String message = messages.get(new Random().nextInt(messages.size()));
				if(message.startsWith("!language"))
					message = LanguageConfig.getString(message.substring("!language ".length()));
				
				Player player = event.getPlayer();
				
				message = StringUtilities.setArgs(message, player.getDisplayName());
				message = PlaceholderFormatter.setPlaceholders(player, message);
				
				event.setQuitMessage(message);
			}
		}
	}

	@EventHandler
	private void onDeath(PlayerDeathEvent event) {
		ConfigurationSection section = PlayersYMLConfig.getConfigurationSection("death");
		if(section == null) return;
		
		if(PlayersYMLConfig.getConfiguration().getBoolean("silent"))
			event.setDeathMessage(null);
		else if(section.getBoolean("messages-enable")) {
			List<String> messages = section.getStringList("messages");
			
			if(!messages.isEmpty()) {
				String message = messages.get(new Random().nextInt(messages.size()));
				if(message.startsWith("!language"))
					message = LanguageConfig.getString(message.substring("!language ".length()));
				
				Player player = event.getEntity();
				
				message = StringUtilities.setArgs(message, player.getDisplayName());
				message = PlaceholderFormatter.setPlaceholders(player, message);
				
				event.setDeathMessage(message);
			}
		}
	}
	
	@EventHandler
	private void Move(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);
		if (!playerConfig.containsLoadedKey("afk") || !playerConfig.getBoolean("afk")) return;

		Location to = e.getTo();
		Location from = e.getFrom();

		if (from.getX() != to.getX() || from.getZ() != to.getZ()) {
			playerConfig.set("afk", false);
			Bukkit.broadcastMessage(LanguageConfig.getString("afk.noLongerAfk", p.getName()));
		}
	}

	@EventHandler
	public void damage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof Player)) return;

		Player player = (Player) entity;
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		if (!config.containsLoadedKey("afk") || !config.getBoolean("afk") || !config.getBoolean(PlayerConfigKey.tGod))
			return;

		event.setCancelled(true);
	}

	@EventHandler
	private void login(PlayerJoinEvent e) {
		Player p = e.getPlayer();

//		if(fileConf.getBoolean("jail")) {
//			//to jail TODO
//		}

		for (Player ps : hide)
			p.hidePlayer(Main.getPlugin(), ps);
	}
}
