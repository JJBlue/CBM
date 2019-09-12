package essentials.modules.world.time;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import essentials.modules.world.WorldConfig;

public class TimeWorldConfig {
	private TimeWorldConfig() {}
	
	public static void load() {
		ConfigurationSection defaultSection = WorldConfig.getConfigurationSection("time");
		
		TimeWorldValues defaultTWV = new TimeWorldValues();
		defaultTWV.setDaySpeedFactor(defaultSection.getDouble("day-speed"));
		defaultTWV.setNightSpeedFactor(defaultSection.getDouble("night-speed"));
		defaultTWV.setSleepSpeedFactor(defaultSection.getDouble("sleep-speed"));
		defaultTWV.setMinPlayerSleepingPercent(defaultSection.getInt("min-player-percent-sleeping"));
		defaultTWV.setUseRealTime(defaultSection.getBoolean("use-real-time"));
		
		TimeWorldManager.defaultTWV = defaultTWV;
		
		for(String worldName : WorldConfig.getConfigurationSection("worlds").getKeys(false)) {
			World world = Bukkit.getWorld(worldName);
			
			ConfigurationSection worldSection = WorldConfig.getConfigurationSection("worlds." + world + "time");
			
			TimeWorldValues worldTWV = new TimeWorldValues();
			worldTWV.setDaySpeedFactor(worldSection.getDouble("day-speed"));
			worldTWV.setNightSpeedFactor(worldSection.getDouble("night-speed"));
			worldTWV.setSleepSpeedFactor(worldSection.getDouble("sleep-speed"));
			worldTWV.setMinPlayerSleepingPercent(worldSection.getInt("min-player-percent-sleeping"));
			worldTWV.setUseRealTime(worldSection.getBoolean("use-real-time"));
			TimeWorldManager.addWorld(world, worldTWV);
		}
	}
	
	public static void unload() {
		TimeWorldManager.clear();
	}
}
