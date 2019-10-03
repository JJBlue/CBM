package essentials.modules.move;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import essentials.language.LanguageConfig;
import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import essentials.utilities.PlayerUtilities;

public class afk implements Listener {
	
	private static Scoreboard scoreboard;
	private static Team team;
	
	public synchronized static void load() {
		if(team != null) return;
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		scoreboard = manager.getMainScoreboard();
		team = scoreboard.getTeam("cbm_afk");
		if(team == null)
			team = scoreboard.registerNewTeam("cbm_afk");
		team.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
	}
	
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
		
		if(team == null)
			load();
		
		if(!value) {
			Bukkit.broadcastMessage(LanguageConfig.getString("afk.noLongerAfk", PlayerUtilities.getName(player)));
			team.removeEntry(player.getName());
		} else {
			team.addEntry(player.getName());
			team.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
			Bukkit.broadcastMessage(LanguageConfig.getString("afk.isNowAfk", PlayerUtilities.getName(player)));
		}
	}
	
	public static boolean isAfk(Player player) {
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		if(config.containsLoadedKey("afk"))
			return config.getBoolean("afk");
		return false;
	}
	
	@EventHandler
	private void damage(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(player);
		if (!playerConfig.containsLoadedKey("afk") || !playerConfig.getBoolean("afk"))
			return;
		
		event.setCancelled(true);
	}
	
	@EventHandler
	private void damage(EntityDamageByBlockEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(player);
		if (!playerConfig.containsLoadedKey("afk") || !playerConfig.getBoolean("afk"))
			return;
		
		event.setCancelled(true);
	}
	
	@EventHandler
	private void Move(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);
		if (!playerConfig.containsLoadedKey("afk") || !playerConfig.getBoolean("afk"))
			return;
		
		setAfk(p, false);
		
//		Location to = event.getTo();
//		Location from = event.getFrom();
//		
//		if (from.getYaw() != to.getYaw() || from.getPitch() != to.getPitch()) {
//			setAfk(p, false);
//			return;
//		}
//		
//		List<Entity> list = p.getNearbyEntities(0.7, 0.7, 0.7);
//		if(list.size() > 0) {
//			event.setCancelled(true);
//			return;
//		}
//		
//		setAfk(p, false);
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
