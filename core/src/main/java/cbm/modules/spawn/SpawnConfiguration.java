package cbm.modules.spawn;

import org.bukkit.configuration.ConfigurationSection;

import cbm.player.PlayersYMLConfig;

public class SpawnConfiguration {
	private SpawnConfiguration() {}
	
	private static ConfigurationSection configuration;
	
	public static void load() {
		configuration = PlayersYMLConfig.getConfigurationSection("spawn");
		
		if(configuration == null)
			configuration = PlayersYMLConfig.getConfiguration().createSection("spawn");
		
		configuration.addDefault("TeleportOnDeath", false);
		configuration.addDefault("TeleportAlwaysOnDeath", false);
		configuration.addDefault("useFirstJoin", false);
	}
	
	public static void unload() {
		configuration = null;
	}
	
	public static ConfigurationSection getConfiguration() {
		return configuration;
	}
}
