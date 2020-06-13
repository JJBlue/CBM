package cbm.modules.claim;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import cbm.config.MainConfig;

public class ClaimConfig {
	private ClaimConfig() {}
	
	private static File file;
	private static FileConfiguration configuration;
	
	public synchronized static void load() {
		if(file != null || configuration != null) return;
		
		file = new File(MainConfig.getDataFolder() + "/claim.yml");
		configuration = YamlConfiguration.loadConfiguration(file);
		
		{
			ConfigurationSection section = getSection("claim");
			section.addDefault("costPerBlock", 255 * 16 * 16 * 0.05);
			section.addDefault("freeBlocks", 255*16*16);
		}
	}
	
	public synchronized static void unload() {
		file = null;
		configuration = null;
	}
	
	public synchronized static void save() {
		if(configuration == null && file == null) return;
		
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ConfigurationSection getSection(String path) {
		ConfigurationSection section = configuration.getConfigurationSection(path);
		if(section != null) return section;
		return configuration.createSection(path);
	}

	public static FileConfiguration getConfiguration() {
		return configuration;
	}
}
