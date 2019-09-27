package essentials.player;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.ConfigHelper;
import essentials.config.MainConfig;
import essentials.modules.display.DisplayManager;
import essentials.modules.spawn.SpawnConfiguration;
import essentials.modules.teleport.TeleportManager;

public class PlayersYMLConfig {
	private static File file;
	private static FileConfiguration configuration;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder(), "players.yml");
		
		if(!file.exists())
			ConfigHelper.extractDefaultConfigs("players", file);
		
		configuration = YamlConfiguration.loadConfiguration(file);
		
		{
			ConfigurationSection section = getConfigurationSectionOrCreate("command");
			section.addDefault("convert", true);
			section.addDefault("convert-use-permission", true);
		}
		
		{
			ConfigurationSection section = getConfigurationSectionOrCreate("chat");
			section.addDefault("enable", true);
			
			section.addDefault("prefix-enable", true);
			section.addDefault("prefix", "\\[%real_time%[time=HH:mm]\\] ");
			section.addDefault("format-enable", true);
			section.addDefault("format", "<%s> %s");
			section.addDefault("suffix-enable", true);
			section.addDefault("suffix", ".");
		}
		
		{
			ConfigurationSection section = getConfigurationSectionOrCreate("death");
			
			section.addDefault("silent", false);
			section.addDefault("messages-enable", false);
			
			List<String> list = new LinkedList<>(); //TODO
			section.addDefault("messages", list);
		}
		
		{
			ConfigurationSection section = getConfigurationSectionOrCreate("join");
			section.addDefault("silent", false);
			section.addDefault("first-time-messages-enable", false);
			
			List<String> list = new LinkedList<>(); //TODO
			section.addDefault("first-time-messages", list);
			
			section.addDefault("messages-enable", false);
			
			list = new LinkedList<>(); //TODO
			section.addDefault("messages", false);
		}
		
		{
			ConfigurationSection section = getConfigurationSectionOrCreate("leave");
			
			section.addDefault("silent", false);
			section.addDefault("messages-enable", false);
			
			List<String> list = new LinkedList<>(); //TODO
			section.addDefault("messages", list);
		}
		
		
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
	
	public static ConfigurationSection getConfigurationSectionOrCreate(String name) {
		ConfigurationSection section = getConfigurationSection(name);
		if(section == null)
			return configuration.createSection(name);
		return section;
	}
	
	public static void save() {
		if(configuration == null) return;
		
		try {
			configuration.save(file);
		} catch (IOException e) {}
	}
}
