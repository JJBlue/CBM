package essentials.player;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.ConfigHelper;
import essentials.config.MainConfig;

import java.io.File;
import java.io.IOException;

public class PlayersYMLConfig {
	private static File file;
	private static FileConfiguration configuration;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder(), "players.yml");
		
		if(!file.exists())
			ConfigHelper.extractDefaultConfigs("players", file);
		
		configuration = YamlConfiguration.loadConfiguration(file);
	}
	
	public static void unload() {
		configuration = null;
		file = null;
	}
	
	public static File getFile() {
		return file;
	}

	public static FileConfiguration getConfiguration() {
		return configuration;
	}
	
	public static ConfigurationSection getConfigurationSection(String name) {
		return configuration.getConfigurationSection(name);
	}
	
	public static void save() {
		try {
			configuration.save(file);
		} catch (IOException e) {}
	}
}
