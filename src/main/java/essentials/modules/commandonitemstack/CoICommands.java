package essentials.modules.commandonitemstack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import essentials.language.LanguageConfig;
import essentials.utilities.BukkitUtilities;
import essentials.utilities.StringUtilities;
import essentials.utilities.chat.ChatUtilities;
import essentials.utilities.chat.ClickAction;
import essentials.utilities.chat.HoverAction;
import essentials.utilities.permissions.PermissionHelper;

public class CoICommands implements CommandExecutor, TabCompleter {
	public static final CoICommands commondsOnItemStack;
	
	static {
		commondsOnItemStack = new CoICommands();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 1) return true;
		
		if(!(sender instanceof Player)) return true;
		
		Player player = (Player) sender;
		
		switch (args[0].toLowerCase()) {
			case "add":
				
				if(args.length < 3) break;
				
				try {
					CoIAction action = CoIAction.valueOf(args[1].toUpperCase());
					String commandExecute = StringUtilities.arrayToStringRange(args, 2, args.length);
					
					CoIManager.addCommand(player.getInventory().getItemInMainHand(), action, commandExecute);
					//TODO ausgabe
				} catch (IllegalArgumentException e) {
					LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
				}
				
				break;
				
			case "remove":
				
				if(args.length < 3) break;
				
				try {
					CoIAction action = CoIAction.valueOf(args[1].toUpperCase());
					String commandExecute = StringUtilities.arrayToStringRange(args, 2, args.length);
					
					CoIManager.removeCommand(player.getInventory().getItemInMainHand(), action, commandExecute);
					//TODO ausgabe
				} catch (IllegalArgumentException e) {
					LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
				}
				
				break;
				
			case "clear":
				
				if(args.length == 1) {
					for(CoIAction action : CoIAction.values())
						CoIManager.clearCommands(player.getInventory().getItemInMainHand(), action);
					//TODO ausgabe
				} else {
					if(args.length < 2) break;
					
					try {
						CoIAction action = CoIAction.valueOf(args[1].toUpperCase());
						CoIManager.clearCommands(player.getInventory().getItemInMainHand(), action);
						//TODO ausgabe
					} catch (IllegalArgumentException e) {
						LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
					}
				}
				
				break;
							
			case "list": {
				for(CoIAction action : CoIAction.values()) {
					List<String> commands = CoIManager.getCommands(player.getInventory().getItemInMainHand(), action);
					
					if(commands.isEmpty()) continue;
					
					//TODO ausgabe
					player.sendMessage("§e" + action.name() + ":");
					
					for(String commandS : commands) {
						ChatUtilities.sendChatMessage(player, "§e  - /" + commandS + " ", ChatUtilities.createExtra(
							ChatUtilities.createClickHoverMessage(
								"§4[-]", HoverAction.SHOW_Text, "Remove", ClickAction.RUN_COMMAND,
								"/" + PermissionHelper.getPluginDefaultCommand() + " coi remove " + action.name() + " " + commandS
							)
						));
					}
				}
				
				break;
			}
		}
		
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("add");
			returnArguments.add("remove");
			returnArguments.add("clear");
			returnArguments.add("list");

		} else {
			switch (args[0]) {
				case "add":
				case "remove":
					
					if(args.length == 2) {
						for(CoIAction action : CoIAction.values())
							returnArguments.add(action.name());
					} else if(args.length == 3) {
						returnArguments = BukkitUtilities.getAvailableCommands(sender);
						returnArguments.add("@c");
					} else {
						if (args[1].toLowerCase().equalsIgnoreCase("@c"))
							return BukkitUtilities.getAvailableCommands(sender);
						
						boolean sendServer = args[2].toLowerCase().equalsIgnoreCase("@c");

						PluginCommand pluginCommand = Bukkit.getPluginCommand(sendServer ? args[3] : args[2]);
						if (pluginCommand == null) break;
						TabCompleter tabCompleter = pluginCommand.getTabCompleter();
						if (tabCompleter == null) break;
						return tabCompleter.onTabComplete(sender, pluginCommand, sendServer ? args[3] : args[2], Arrays.copyOfRange(args, sendServer ? 4 : 3, args.length));
					}
					
					break;
				case "clear":
					
					for(CoIAction action : CoIAction.values())
						returnArguments.add(action.name());
					
					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
