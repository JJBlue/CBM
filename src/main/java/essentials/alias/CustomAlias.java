package essentials.alias;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.MainConfig;
import essentials.utilities.BukkitUtilities;

public class CustomAlias {
	static File file;
	static FileConfiguration fileConfiguration;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder(), "CustomAlias.yml");
		fileConfiguration = YamlConfiguration.loadConfiguration(file);
		
		/*
		 * 	@c
		 * 	$[argsNumber]
		 * 	!delay
		 */
		
		
		fileConfiguration.options().copyDefaults(true);
		try {
			fileConfiguration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void registerCommand(String name) {
		BukkitUtilities.registerCommand("cbm", new Command(name) {
			@Override
			public boolean execute(CommandSender sender, String commandLabel, String[] args) {
				List<?> commands = fileConfiguration.getList(commandLabel + ".Cmds");
				boolean useExtraPermission = fileConfiguration.getBoolean(commandLabel + ".Perm");
				
				
				//TODO
				return true;
			}
		});
	}
	
	public static void unload() {
		file = null;
		fileConfiguration = null;
	}
}
