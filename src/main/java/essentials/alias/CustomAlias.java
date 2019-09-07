package essentials.alias;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.MainConfig;
import essentials.main.Main;

public class CustomAlias {
	static File file;
	static FileConfiguration fileConfiguration;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder(), "CustomAlias.yml");
		fileConfiguration = YamlConfiguration.loadConfiguration(file);
		
		String prefix = "CustomAlias.";
		
		String commandAlias = null;
		
		/*
		 * 	@c
		 * 	$[argsNumber]
		 * 	!delay
		 */
		
		List<?> commands = fileConfiguration.getList(prefix + commandAlias + ".Cmds");
		boolean useExtraPermission = fileConfiguration.getBoolean(prefix + commandAlias + ".Perm");
		
		Bukkit.broadcastMessage("#####################################################");
		Map<String, String[]> aliases = Main.getPlugin().getServer().getCommandAliases();
		for(String a : aliases.keySet()) {
			for(String s : aliases.get(a))
				Bukkit.broadcastMessage(a + " " + s);
		}
		
		
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
