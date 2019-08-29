package essentials.alias;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.MainConfig;

public class CustomAlias {
	static File file;
	static FileConfiguration fileConfiguration;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder(), "CustomAlias.yml");
		fileConfiguration = YamlConfiguration.loadConfiguration(file);
		
		String prefix = "CustomAlias.";
		
		String commandAlias = null;
		
		/*
		 * 	fromConsole!
		 * 	$[argsNumber]
		 * 	delay!
		 */
		
		List<?> commands = fileConfiguration.getList(prefix + commandAlias + ".Cmds");
		boolean useExtraPermission = fileConfiguration.getBoolean(prefix + commandAlias + ".Perm");
		
		
		
		
		fileConfiguration.options().copyDefaults(true);
		try {
			fileConfiguration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void unload() {
		file = null;
		fileConfiguration = null;
	}
}
