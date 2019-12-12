package essentials.modules.sudo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import essentials.utilities.BukkitUtilities;
import essentials.utilities.StringUtilities;

public class SudoCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 1) return true;
		
		switch (args[0].toLowerCase()) {
			case "sudo-": { //Only execute command over player/console
				
				if (args.length < 3) break;
				
				if (args[1].equalsIgnoreCase("@c"))
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), StringUtilities.arrayToString(Arrays.copyOfRange(args, 2, args.length)));
				else if(args[1].equalsIgnoreCase("@a")) {
					for(Player player : Bukkit.getOnlinePlayers())
						Bukkit.dispatchCommand(player, StringUtilities.arrayToStringRange(args, 2, args.length));
				} else {
					Player player = Bukkit.getPlayer(args[1]);
					if (player == null) return true;
					Bukkit.dispatchCommand(player, StringUtilities.arrayToStringRange(args, 2, args.length));
				}
				
				break;
			}	
			case "sudo": { //Execute command with your permissions
				
				if (args.length < 3) return true;
				
				if (args[1].equalsIgnoreCase("@c"))
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), StringUtilities.arrayToString(Arrays.copyOfRange(args, 2, args.length)));
				else if(args[1].equalsIgnoreCase("@a")) {
					for(Player sudoPlayer : Bukkit.getOnlinePlayers())
						SudoManager.execute(sender, sudoPlayer, StringUtilities.arrayToStringRange(args, 2, args.length));
				} else {
					Player sudoPlayer = Bukkit.getPlayer(args[1]);
					if (sudoPlayer == null) return true;
					SudoManager.execute(sender, sudoPlayer, StringUtilities.arrayToStringRange(args, 2, args.length));
				}
				
				break;
			}
			case "sudo+": { //Set all players temporaly to operator, WARNING! BUGS & CRITICAL SITUATION
				
				SudoManager.setTmpOperators();
				
				if (args[1].equalsIgnoreCase("@c"))
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), StringUtilities.arrayToString(Arrays.copyOfRange(args, 2, args.length)));
				else if(args[1].equalsIgnoreCase("@a")) {
					for(Player player : Bukkit.getOnlinePlayers())
						Bukkit.dispatchCommand(player, StringUtilities.arrayToStringRange(args, 2, args.length));
				} else {
					Player player = Bukkit.getPlayer(args[1]);
					if (player == null) return true;
					Bukkit.dispatchCommand(player, StringUtilities.arrayToStringRange(args, 2, args.length));
				}
				
				SudoManager.removeTmpOperators();
				
				break;
			}
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("sudo");
			returnArguments.add("sudo+");
			returnArguments.add("sudo-");

		} else {
			switch(args[0].toLowerCase()) {
				case "sudo":
				case "sudo-":
				case "sudo+":
					
					if(args.length == 2) {
						for (Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
						returnArguments.add("@c");
						returnArguments.add("@a");
					} else if(args.length == 3) {
						returnArguments = BukkitUtilities.getAvailableCommands(sender);
					} else {
						PluginCommand pluginCommand = Bukkit.getPluginCommand(args[2]);
						if (pluginCommand == null) break;
						TabCompleter tabCompleter = pluginCommand.getTabCompleter();
						if (tabCompleter == null) break;
						return tabCompleter.onTabComplete(sender, pluginCommand, args[2], Arrays.copyOfRange(args, 3, args.length));
					}
					
					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
