package cbm.modules.world.time;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import cbm.modules.world.WorldConfig;

public class TimeWorldConfig {
	private TimeWorldConfig() {}

	public static void load() {
		ConfigurationSection defaultSection = WorldConfig.getConfigurationSection("time");

		if (defaultSection != null) {
			TimeWorldValues defaultTWV = new TimeWorldValues();
			defaultTWV.setDaySpeedFactor(defaultSection.getDouble("day-speed"));
			defaultTWV.setNightSpeedFactor(defaultSection.getDouble("night-speed"));
			defaultTWV.setUseRealTime(defaultSection.getBoolean("use-real-time"));
			
			defaultTWV.setSleepSpeedFactor(defaultSection.getDouble("sleep.speed"));
			defaultTWV.setMinPlayerSleepingPercent(defaultSection.getInt("sleep.min-player-percent"));
			defaultTWV.setUseBossBar(defaultSection.getBoolean("sleep.showBossBar"));
			defaultTWV.setSleepWithAFK(defaultSection.getBoolean("sleep.withAFK"));

			TimeWorldManager.defaultTWV = defaultTWV;
		} else {
			TimeWorldManager.defaultTWV = new TimeWorldValues();
		}
			
		ConfigurationSection worldsSection = WorldConfig.getConfigurationSection("worlds");

		if (worldsSection != null) {
			for (String worldName : worldsSection.getKeys(false)) {
				World world = Bukkit.getWorld(worldName);

				ConfigurationSection worldSection = WorldConfig.getConfigurationSection("worlds." + worldName + ".time");
				if (worldSection == null) continue;

				if (!worldSection.getBoolean("enable")) continue;

				TimeWorldValues worldTWV = new TimeWorldValues();
				worldTWV.setDaySpeedFactor(worldSection.getDouble("day-speed"));
				worldTWV.setNightSpeedFactor(worldSection.getDouble("night-speed"));
				worldTWV.setUseRealTime(worldSection.getBoolean("use-real-time"));
				
				worldTWV.setSleepSpeedFactor(worldSection.getDouble("sleep.speed"));
				worldTWV.setMinPlayerSleepingPercent(worldSection.getInt("sleep.min-player-percent"));
				worldTWV.setUseBossBar(worldSection.getBoolean("sleep.showBossBar"));
				worldTWV.setSleepWithAFK(worldSection.getBoolean("sleep.withAFK"));
				
				TimeWorldManager.addWorld(world, worldTWV);
			}
		}

		TimeWorldManager.startTimer();
	}

	public static void unload() {
		TimeWorldManager.clear();
	}
}
