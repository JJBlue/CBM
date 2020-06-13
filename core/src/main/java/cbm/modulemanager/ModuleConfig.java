package cbm.modulemanager;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import cbm.config.MainConfig;

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
		}
		
		return configuration.getBoolean(path);
	}
	
	public static void setAutoload(String id, boolean value) {
		configuration.set("autoload." + id, value);
	}
	
	public static void save() {
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
