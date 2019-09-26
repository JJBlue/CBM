package essentials.player;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.ConfigHelper;
import essentials.config.MainConfig;
import essentials.modules.display.DisplayManager;
import essentials.modules.spawn.SpawnConfiguration;
import essentials.modules.teleport.TeleportManager;

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
		
		configuration.addDefault("join.silent", false);
		configuration.addDefault("death.silent", false);
		
		// Load subclasses
		DisplayManager.load();
		TeleportManager.load();
		SpawnConfiguration.load();
		
		configuration.options().copyDefaults(true);
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void unload() {
		TeleportManager.unload();
		SpawnConfiguration.unload();
		
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
		if(configuration == null) return null;
		return configuration.getConfigurationSection(name);
	}
	
	public static void save() {
		if(configuration == null) return;
		
		try {
			configuration.save(file);
		} catch (IOException e) {}
	}
}
