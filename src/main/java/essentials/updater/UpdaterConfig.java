package essentials.updater;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.MainConfig;

public class UpdaterConfig {
	static File configFile;
	static FileConfiguration configuration;
	
	public static void load() {
		configFile = new File(MainConfig.getDataFolder(), "Updater.yml");
		configuration = YamlConfiguration.loadConfiguration(configFile);
		
		configuration.addDefault("downloadFolder", "-");
		
		//TODO addDefault CBM Updater
		/*
		 * YAML Style?
		 * 		UpdatePlugins:
		 *  	  <ID>:
		 * 	 	    name: <Name>
		 * 	 	    URL: (<URL>)
		 * 		  <ID>:
		 * 			...
		 * 
		 * or
		 * 		UpdatePlugins:
		 * 		- ID: <ID>
		 * 		  name: <Name>
		 * 		  URL: (<URL>)
		 * 		- ID: <ID>
		 * 		  ...
		 * 
		 */
		
		configuration.options().copyDefaults(true);
		save();
		
		String downloadFolder = configuration.getString("downloadFolder");
		if(downloadFolder == null || downloadFolder.isEmpty() || downloadFolder.equalsIgnoreCase("-"))
			downloadFolder = "./plugins/" + MainConfig.getDataFolder() + "/download";
		
		ServerManager.downloadFolder = new File(downloadFolder);
		ServerManager.downloadFolder.mkdirs();
		
		//TODO load UpdatePlugins
	}
	
	public static void unload() {
		save();
		configFile = null;
		configuration = null;
	}
	
	private static void save() {
		try {
			configuration.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
