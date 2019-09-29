package essentials.modules.teleport;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import essentials.language.LanguageConfig;
import essentials.main.Main;
import essentials.modules.particles.ParticleEffectsManager;
import essentials.modules.particles.ParticlePosInfoDummy;
import essentials.player.PlayersYMLConfig;

public class TeleportManager {
	private TeleportManager() {}
	
	private static ConfigurationSection configuration;
	private static Map<Entity, TeleportInformation> standStill = Collections.synchronizedMap(new HashMap<>()); //in seconds
	private static Map<Entity, Integer> cooldowns = Collections.synchronizedMap(new HashMap<>()); //in seconds
	private static int taskID;
	
	public static void load() {
		configuration = PlayersYMLConfig.getConfigurationSection("teleport");
		if(configuration == null)
			configuration = PlayersYMLConfig.getConfiguration().createSection("teleport");
		
		configuration.addDefault("timeStandStillWhileTeleporting", 0);
		configuration.addDefault("cooldown", 0);
		configuration.addDefault("useParticles", false);
	}
	
	public static void unload() {
		configuration = null;
		standStill.clear();
		cooldowns.clear();
	}
	
	public static void teleport(Entity entity, Location location) {
		if(entity == null || location == null) return;
		
		if(hasCooldown(entity)) {
			LanguageConfig.sendMessage(entity, "teleport.cooldown", getCooldown(entity) + "");
			return;
		}
		if(mustStandStillWhileTeleporting() && !isStandStillWhileTeleporting(entity)) {
			standStill.put(entity, new TeleportInformation(location, getTimeStandStillWhileTeleporting()));
			startTimer();
			LanguageConfig.sendMessage(entity, "teleport.standStill", getTimeStandStillWhileTeleporting(entity) + "");
			return;
		}
		
		entity.teleport(location);
		LanguageConfig.sendMessage(entity, "spawn.teleport");
		if(hasCooldown()) {
			cooldowns.put(entity, getCooldown());
			startTimer();
		}
	}
	
	public static int getTimeStandStillWhileTeleporting() {
		return configuration.getInt("timeStandStillWhileTeleporting");
	}
	
	public static int getTimeStandStillWhileTeleporting(Entity entity) {
		synchronized (standStill) {
			if(standStill.containsKey(entity))
				return standStill.get(entity).getCooldown();
		}
		
		return getTimeStandStillWhileTeleporting();
	}
	
	public static void setTimeStandStillWhileTeleporting(Entity entity, int value) {
		synchronized (standStill) {
			if(standStill.containsKey(entity))
				standStill.get(entity).setCooldown(value);
		}
	}
	
	public static boolean mustStandStillWhileTeleporting() {
		return getTimeStandStillWhileTeleporting() > 0;
	}
	
	public static boolean isStandStillWhileTeleporting(Entity entity) {
		return getTimeStandStillWhileTeleporting(entity) <= 0;
	}
	
	public static int getCooldown() {
		return configuration.getInt("cooldown");
	}
	
	public static int getCooldown(Entity entity) {
		synchronized (cooldowns) {
			if(cooldowns.containsKey(entity)) {
				int value = cooldowns.get(entity);
				if(value <= 0)
					cooldowns.remove(entity);
				return value;
			}
		}
		return 0;
	}
	
	public static void setCooldown(Entity entity, int value) {
		cooldowns.put(entity, value);
	}
	
	public static boolean hasCooldown() {
		return getCooldown() > 0;
	}
	
	public static boolean hasCooldown(Entity entity) {
		return hasCooldown() && getCooldown(entity) > 0;
	}
	
	public static void removeEntity(Player player) {
		standStill.remove(player);
	}
	
	public synchronized static void startTimer() {
		if(taskID > 0) return;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
			if(cooldowns.isEmpty() && standStill.isEmpty()) stopTimer();
			
			synchronized (cooldowns) {
				cooldowns.entrySet().removeIf(e -> e.getValue() <= 1);
				
				cooldowns.forEach((entity, value) -> {
					cooldowns.put(entity, value - 1);
				});
			}
			
			synchronized (standStill) {
				standStill.forEach((entity, info) -> {
					if(info.getCooldown() <= 0) {
						entity.teleport(info.getLocation());
						info.setCooldown(-1);
					} else {
						info.setCooldown(info.getCooldown() - 1);
						
						//TODO better particles
						if(configuration.getBoolean("useParticles")) {
							ParticlePosInfoDummy dummy = new ParticlePosInfoDummy(entity.getWorld());
							
							ParticleEffectsManager.spawnSpiral(
									dummy,
									Particle.REDSTONE,
									entity.getLocation(),
									2,
									0.1,
									1,
									360d/180d,
									1,
									Color.RED,
									1
								);
							ParticleEffectsManager.spawnCircle(
								dummy, Particle.REDSTONE, entity.getLocation(), 1, 60, 1, Color.WHITE, 1
							);
						}
					}
				});
				
				standStill.entrySet().removeIf(e -> e.getValue().getCooldown() < 0);
			}
		}, 0L, 20L);
	}
	
	public synchronized static void stopTimer() {
		if(taskID <= 0) return;
		
		Bukkit.getScheduler().cancelTask(taskID);
		taskID = -1;
	}
}
