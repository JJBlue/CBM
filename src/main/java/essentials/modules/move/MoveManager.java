package essentials.modules.move;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import essentials.main.Main;
import essentials.player.PlayersYMLConfig;

public class MoveManager implements Listener {
	
	private static Map<Player, LocalDateTime> standStill = Collections.synchronizedMap(new HashMap<>());
	private static ConfigurationSection section;
	private static int taskID;
	
	public static void load() {
		section = PlayersYMLConfig.getConfigurationSectionOrCreate("afk");
		section.addDefault("autoEnable", true);
		section.addDefault("after", 60*5);
		
		if(section.getBoolean("autoEnable"))
			start();
	}
	
	public static void unload() {
		stop();
		section = null;
		standStill.clear();
	}
	
	public synchronized static void start() {
		if(taskID > 0) return;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
			LocalDateTime localDateTime = LocalDateTime.now();
			
			standStill.forEach((player, time) -> {
				if(section == null) return;
				if(afk.isAfk(player)) return;
				
				long seconds = Duration.between(time, localDateTime).toSeconds();
				if(section.getLong("after") <= seconds)
					afk.setAfk(player, true);
			});
			
			for(Player player : Bukkit.getOnlinePlayers()) {
				if(standStill.containsKey(player)) continue;
				standStill.put(player, localDateTime);
			}
		}, 0L, 20L);
	}
	
	public synchronized static void stop() {
		if(taskID <= 0) return;
		
		Bukkit.getScheduler().cancelTask(taskID);
		taskID = -1;
	}
	
	@EventHandler
	public void move(PlayerMoveEvent event) {
		if(standStill.containsKey(event.getPlayer()))
			standStill.remove(event.getPlayer());
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event) {
		standStill.remove(event.getPlayer());
	}
}
