package essentials.modulemanager;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.MainConfig;

public class ModuleConfig {
	
	static File file;
	static FileConfiguration configuration;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder() + "/modules.yml");
		configuration = YamlConfiguration.loadConfiguration(file);
		configuration.options().copyDefaults(true);
	}
	
	public static void unload() {
		configuration = null;
		file = null;
	}
	
	public static boolean shouldLoadModule(String id) {
		String path = "autoload." + id;
		
		if(!configuration.contains(path)) {
			configuration.addDefault(path, false);
			try {
				configuration.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return configuration.getBoolean(path);
	}
}
