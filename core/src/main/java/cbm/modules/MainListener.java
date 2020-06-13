package cbm.modules;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import cbm.depend.Depend;
import cbm.language.LanguageConfig;
import cbm.main.Main;
import cbm.player.PlayerConfig;
import cbm.player.PlayerConfigKey;
import cbm.player.PlayerManager;
import cbm.player.PlayersYMLConfig;
import cbm.utilities.StringUtilities;
import cbm.utilities.permissions.PermissionHelper;
import cbm.utilities.placeholder.PlaceholderFormatter;

public class MainListener implements Listener {
	@EventHandler
	private void Chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		PlayerConfig playerConfig = PlayerManager.getConfig(p);

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
		} else if(section.getBoolean("prefix-enable") || section.getBoolean("format-enable") || section.getBoolean("suffix-enable")) {
			StringBuilder builder = new StringBuilder();
			
			if(section.getBoolean("prefix-enable"))
				builder.append(Depend.getPrefix(player));
			
			if(section.getBoolean("format-enable")) {
				String format = PlaceholderFormatter.setPlaceholders(player, section.getString("format"));
				
				if(format != null)
					builder.append(format);
			} else
				builder.append(event.getFormat());
			
			if(section.getBoolean("suffix-enable"))
				builder.append(Depend.getSuffix(player));
			
			event.setFormat(builder.toString());
		}
	}
	
	private void convertChat(AsyncPlayerChatEvent event) {
		ConfigurationSection section = PlayersYMLConfig.getConfigurationSection("command");
		if(section == null) return;

		Player player = event.getPlayer();
		String text = event.getMessage();
		if(text == null) return;
		
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
		
		boolean leaveSilent = section.getBoolean("silent");
		
		if(!leaveSilent) {
			PlayerConfig config = PlayerManager.getConfig(event.getPlayer());
			leaveSilent = config.getBoolean(PlayerConfigKey.joinSilent);
		}
		
		if(leaveSilent)
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
	public void damage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof Player)) return;

		Player player = (Player) entity;
		PlayerConfig config = PlayerManager.getConfig(player);
		if (!config.containsLoadedKey("afk") || !config.getBoolean("afk") || !config.getBoolean(PlayerConfigKey.tGod))
			return;

		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onKick(PlayerKickEvent e) {
		Player p = e.getPlayer();

		if (PermissionHelper.hasPermission(p, "kickprotection")) {
			e.setCancelled(true);
		}
	}
}
