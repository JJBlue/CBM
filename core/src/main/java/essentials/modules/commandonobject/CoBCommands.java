package essentials.modules.commandonobject;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import essentials.language.LanguageConfig;
import essentials.utilities.BukkitUtilities;
import essentials.utilities.StringUtilities;
import essentials.utilities.chat.ChatUtilities;
import essentials.utilities.chat.ClickAction;
import essentials.utilities.chat.HoverAction;
import essentials.utilities.permissions.PermissionHelper;

public class CoBCommands implements TabExecutor {

	//MoveEvent Listener ist im MoveEvents
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		Player p = null;

		if (sender instanceof Player)
			p = (Player) sender;

		if (args.length < 1) return false;

		switch (args[0].toLowerCase()) {
			case "list":

				if(p == null) break;
				
				Block targetblock = p.getTargetBlock(null, 50);
				if (targetblock == null) break;

				CoBBlock cobblock = CommandOnBlock.getCommandOnBlock(targetblock.getLocation());
				if(cobblock == null) break;

				LanguageConfig.sendMessage(p, "cob.list");
				
				for(CoBAction action : CoBAction.values()) {
					List<String> commands = cobblock.getCommands(action);
					if(commands.isEmpty()) continue;
					
					p.sendMessage("§e  " + action.name() + ":");
					
					for(String commandS : commands) {
						ChatUtilities.sendChatMessage(p, "§e  - /" + commandS + " ", ChatUtilities.createExtra(
							ChatUtilities.createClickHoverMessage(
								"§4[-]", HoverAction.SHOW_Text, "Remove", ClickAction.RUN_COMMAND,
								"/" + PermissionHelper.getPluginDefaultCommand() + " cob remove " + action.name() + " " + commandS
							)
						));
					}
				}

				break;

			case "clear":

				if(p == null) break;
				
				targetblock = p.getTargetBlock(null, 50);
				CommandOnBlock.clear(targetblock.getLocation());
				LanguageConfig.sendMessage(sender, "cob.clear");

				break;

			case "add":

				if (args.length < 2 || p == null) break;

				targetblock = p.getTargetBlock(null, 50);
				if(targetblock == null) break;
				
				try {
					CoBAction action = CoBAction.valueOf(args[1]);
					String command = StringUtilities.arrayToStringRange(args, 2, args.length);
					
					CommandOnBlock.addCommand(targetblock.getLocation(), action, command);
					LanguageConfig.sendMessage(sender, "cob.add", action.name(), command);
				} catch (IllegalArgumentException e) {
					LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
				}

				break;

			case "remove":

				if (args.length < 2 || p == null) break;

				targetblock = p.getTargetBlock(null, 50);
				if(targetblock == null) break;

				try {
					CoBAction action = CoBAction.valueOf(args[1]);
					String command = StringUtilities.arrayToStringRange(args, 2, args.length);
					
					CommandOnBlock.removeCommand(targetblock.getLocation(), action, command);
					LanguageConfig.sendMessage(sender, "cob.remove", action.name(), command);
				} catch (IllegalArgumentException e) {
					LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
				}
				
				break;

			case "save":

				CommandOnBlock.save();
				LanguageConfig.sendMessage(sender, "text.save-complete", args);
				break;

			default:
				help(sender);
		}

		return true;
	}

	public static void help(CommandSender sender) {
		sender.sendMessage("§4@c <command> = Command over the consol,@a = All players,  @p = Player, @w = World");
		sender.sendMessage("/cos add <command>");
		sender.sendMessage("/cos remove <command>");
		sender.sendMessage("/cos clear");
		sender.sendMessage("/cos list");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> result = null;
		boolean finished = false;
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("list");
			returnArguments.add("clear");
			returnArguments.add("add");
			returnArguments.add("remove");
			returnArguments.add("save");
		} else {
			switch (args[0].toLowerCase()) {
				case "add":
				case "remove":
					switch (args.length) {
						case 2:

							for (CoBAction action : CoBAction.values())
								returnArguments.add(action.name());

							break;

						case 3:

							returnArguments = BukkitUtilities.getAvailableCommands(sender);
							returnArguments.add("@c");

							break;

						case 4:

							if (args[1].toLowerCase().equalsIgnoreCase("@c")) {
								result = BukkitUtilities.getAvailableCommands(sender);
								finished = true;
								break;
							}

						default:

							boolean sendServer = args[1].toLowerCase().equalsIgnoreCase("@c");

							PluginCommand command = Bukkit.getPluginCommand(sendServer ? args[3] : args[2]);
							if (command == null) break;
							TabCompleter tabCompleter = command.getTabCompleter();
							if (tabCompleter == null) break;
							result = tabCompleter.onTabComplete(sender, command, sendServer ? args[3] : args[2], Arrays.copyOfRange(args, sendServer ? 4 : 3, args.length));
							finished = true;
							break;
					}

					break;
			}
		}
		if (!finished) {

			returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

			returnArguments.sort(Comparator.naturalOrder());

			result = returnArguments;
		}
		return result;
	}
}
