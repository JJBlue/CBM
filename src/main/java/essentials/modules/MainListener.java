package essentials.modules;

import essentials.language.LanguageConfig;
import essentials.main.Main;
import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;
import essentials.utilities.permissions.PermissionHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;

public class MainListener implements Listener {
	public final static Set<Player> hide = new HashSet<>();

	@EventHandler
	private void Chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);

		if (playerConfig.getBoolean(PlayerConfigKey.tMute))
			e.setCancelled(true);

		String text = e.getMessage();

		if (PermissionHelper.hasPermission(p, "CommandTypingError")) { //TODO disable in config, maybe?
			if (text.startsWith("\\7")) {
				e.setMessage(text.replaceFirst("\\", ""));
			} else if (text.startsWith("7")) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> p.performCommand(text.replaceFirst("7", "")));

				LanguageConfig.sendMessage(p, "command.TypingError");
				e.setCancelled(true);
				return;
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
//		} else if(!p.hasPlayedBefore()) {
//			//Spawn TODO
//		}

		for (Player ps : hide)
			p.hidePlayer(Main.getPlugin(), ps);
	}
}
