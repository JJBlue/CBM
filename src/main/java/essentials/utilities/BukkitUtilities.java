package essentials.utilities;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
import java.util.List;

public class BukkitUtilities {
	public static List<String> getAvailableCommands(CommandSender sender){
		List<String> commands = new LinkedList<>();
		
		for(HelpTopic cmdLabel : Bukkit.getServer().getHelpMap().getHelpTopics()) {
			if(sender != null && !cmdLabel.canSee(sender)) continue;
			String name = cmdLabel.getName();
			if(name.startsWith("/"))
				name = name.substring(1);
			commands.add(name);
		}
		
		return commands;
	}
	
	public static List<String> getAvailableCommands(Plugin plugin, CommandSender sender){
		List<String> commands = new LinkedList<>();
		
		for(HelpTopic cmdLabel : Bukkit.getServer().getHelpMap().getHelpTopics()) {
			if(sender != null && !cmdLabel.canSee(sender)) continue;
			String name = cmdLabel.getName();
			if(name.startsWith("/"))
				name = name.substring(1);
			commands.add(name);
		}
		
		return commands;
	}
}
