package essentials.modules.world.time;

import org.bukkit.configuration.ConfigurationSection;

import essentials.modules.world.WorldConfig;

public class TimeWorldConfig {
	private TimeWorldConfig() {}
	
	public static void load() {
		ConfigurationSection defaultSection = WorldConfig.getConfigurationSection("time.default");
		
		TimeWorldValues defaultTWV = new TimeWorldValues();
		defaultTWV.setDaySpeedFactor(defaultSection.getDouble("day-speed"));
		defaultTWV.setNightSpeedFactor(defaultSection.getDouble("night-speed"));
		defaultTWV.setSleepSpeedFactor(defaultSection.getDouble("sleep-speed"));
		defaultTWV.setStartMinPlayerSleeping(defaultSection.getInt("min-player-sleeping"));
		defaultTWV.setUseRealTime(defaultSection.getBoolean("use-real-time"));
		
		TimeWorldManager.defaultTWV = defaultTWV;
	}
}
