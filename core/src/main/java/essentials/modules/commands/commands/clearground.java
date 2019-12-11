package essentials.modules.commands.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import essentials.language.LanguageConfig;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabcompleter.STabCompleter;

public class clearground {
	//clearground <World|@a>
	//alternativ you could use /kill @e[type=item,distance..10]
	public static void register() {
		CommandExecutor executor = (sender, cmd, cmdLabel, args) -> {
			if(args.length < 1) return true;
			
			List<World> worlds = new LinkedList<>();
			
			if(args.length == 1) {
				Location location = null;
				
				if(sender instanceof Player) {
					location = ((Player) sender).getLocation();
				} else if(sender instanceof BlockCommandSender) {
					location = ((BlockCommandSender) sender).getBlock().getLocation();
				}
				
				if(location == null) return true;
			} else if(args.length == 2) {
				if(args[1].equalsIgnoreCase("@a")) {
					worlds.addAll(Bukkit.getWorlds());
				} else {
					worlds.add(Bukkit.getWorld(args[1]));
				}
			}
			
			long count = 0;
			
			for(World world : worlds) {
				for(Entity entity : world.getEntitiesByClass(Item.class)) {
					entity.remove();
					count++;
				}
			}
			
			LanguageConfig.sendMessage(sender, "clearground.cleared", count + "");
			
			return true;
		};
		
		TabCompleter completer = (sender, cmd, alias, args) -> {
			return STabCompleter.sortAndRemove(STabCompleter.getWorlds(), args[args.length - 1]);
		};
		
		CommandManager.register("clearground", CommandManager.getTabExecutor(executor, completer));
	}
}
