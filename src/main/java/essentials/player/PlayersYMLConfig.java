package essentials.player;

import essentials.config.ConfigHelper;
import essentials.config.MainConfig;
import essentials.modules.display.DisplayManager;
import essentials.modules.move.MoveManager;
import essentials.modules.spawn.SpawnConfiguration;
import essentials.modules.teleport.TeleportManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
			
			section.addDefault("prefix-enable", false);
			section.addDefault("prefix", "[%real_time%[time=HH:mm]] ");
			section.addDefault("format-enable", false);
			section.addDefault("format", "<\\%s> \\%s");
			section.addDefault("suffix-enable", false);
			section.addDefault("suffix", ".");
		}
		
		{
			ConfigurationSection section = getConfigurationSectionOrCreate("death");
			
			section.addDefault("silent", false);
			section.addDefault("messages-enable", false);
			
			List<String> list = new LinkedList<>();
			list.add("$1 died");
			section.addDefault("messages", list);
		}
		
		{
			ConfigurationSection section = getConfigurationSectionOrCreate("join");
			section.addDefault("silent", false);
			section.addDefault("first-time-messages-enable", false);
			
			List<String> list = new LinkedList<>();
			list.add("$1 joined the first time");
			section.addDefault("first-time-messages", list);
			
			section.addDefault("messages-enable", false);
			
			list = new LinkedList<>();
			list.add("$1 joined");
			section.addDefault("messages", false);
		}
		
		{
			ConfigurationSection section = getConfigurationSectionOrCreate("leave");
			
			section.addDefault("silent", false);
			section.addDefault("messages-enable", false);
			
			List<String> list = new LinkedList<>();
			list.add("$1 leaved");
			section.addDefault("messages", list);
		}
		
		
		// Load subclasses
		DisplayManager.load();
		TeleportManager.load();
		SpawnConfiguration.load();
		MoveManager.load();
		
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
		MoveManager.unload();
		
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
		} catch (IOException ignored) {}
	}
}
