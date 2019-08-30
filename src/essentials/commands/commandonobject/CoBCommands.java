package essentials.commands.commandonobject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import essentials.utilities.BukkitUtilities;
import essentials.utilities.chat.ChatUtilities;
import essentials.utilities.chat.ClickAction;
import essentials.utilities.chat.HoverAction;

public class CoBCommands implements CommandExecutor, TabCompleter {
	public final static CoBCommands commandOnBlock;
	
	static {
		commandOnBlock = new CoBCommands();
	}
	
	//MoveEvent Listener ist im MoveEvents
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		Player p = null;
		
		if(sender instanceof Player)
			p = (Player)sender;
		
		if(args.length < 1) return false;
		
		switch(args[0].toLowerCase()) {
			case "help":
				sender.sendMessage("§4@c <command> = Command over the consol,@a = All players,  @p = Player, @w = World");
				sender.sendMessage("/cos add <command>");
				sender.sendMessage("/cos remove <command>");
				sender.sendMessage("/cos clear");
				sender.sendMessage("/cos list");
				
				break;
				
			case "list":
				
				Block targetblock = p.getTargetBlock(null,50);
				p.sendMessage("Auf dem Item sind folgende Commands:");
				
				for(CoBCommandInfo commandInfo : CommandOnBlock.getCommandInfos(targetblock.getLocation())) {
					ChatUtilities.sendChatMessage(p, "  /" + commandInfo.command + " ",
						ChatUtilities.createExtra(
							ChatUtilities.createClickHoverMessage("§4[-]", HoverAction.SHOW_Text, "Remove Command", ClickAction.RUN_COMMAND, "/all cob remove " + commandInfo.command)
						)
					);
				}
				
				break;
				
			case "clear":
				
				targetblock = p.getTargetBlock(null,50);
				CommandOnBlock.clear(targetblock.getLocation());
				
				break;
				
			case "add":
				
				if(args.length < 2) break;
				
				targetblock = p.getTargetBlock(null,50);
				
				int anzahl = args.length;
				
				String argsstring = "";
				for(int y = 2; y <= anzahl; y++){
					int y2 = y - 1;
					if(y == 2)
						argsstring = args[y2];
					else
						argsstring = argsstring + " " + args[y2];
				}
				
				CommandOnBlock.addCommand(targetblock.getLocation(), argsstring);
				
				p.sendMessage("Auf dem Item sind nun folgende Commands:");
				for(CoBCommandInfo commandInfo : CommandOnBlock.getCommandInfos(targetblock.getLocation()))
					p.sendMessage("/" + commandInfo.command);
				
				break;
				
			case "remove":
				
				if(args.length < 2) break;
				
				targetblock = p.getTargetBlock(null,50);
				
				argsstring = "";
				
				for(int y = 2; y <= args.length; y++){
					int y2 = y - 1;
					if(y == 2)
						argsstring = args[y2];
					else
						argsstring = argsstring + " " + args[y2];
				}
				
				CommandOnBlock.removeCommand(targetblock.getLocation(), argsstring);
				p.sendMessage("§4Command deleted");
				
				p.sendMessage("Auf dem Item sind nun folgende Commands:");
				
				for(CoBCommandInfo commandInfo : CommandOnBlock.getCommandInfos(targetblock.getLocation()))
					p.sendMessage("/" + commandInfo.command);
				
				break;
				
			case "save":
				
				CommandOnBlock.save();
				p.sendMessage("Saved");
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
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("list");
			returnArguments.add("clear");
			returnArguments.add("add");
			returnArguments.add("remove");
			returnArguments.add("save");
		} else {
			switch(args[0].toLowerCase()) {
				case "add":
				case "remove":
					switch (args.length) {
						case 2:
							
							returnArguments = BukkitUtilities.getAvailableCommands(sender);
							returnArguments.add("@c");
							
							break;
							
						case 3:
							
							if(args[1].toLowerCase().equalsIgnoreCase("@c"))
								return BukkitUtilities.getAvailableCommands(sender);
	
						default:
							
							boolean sendServer = args[1].toLowerCase().equalsIgnoreCase("@c");
							
							PluginCommand command = Bukkit.getPluginCommand(sendServer ? args[2] : args[1]);
							if(command == null) break;
							TabCompleter tabCompleter = command.getTabCompleter();
							if(tabCompleter == null) break;
							return tabCompleter.onTabComplete(sender, command, sendServer ? args[2] : args[1], Arrays.copyOfRange(args, sendServer ? 3 : 2, args.length));
					}
					
					break;
			}
		}
		
		returnArguments.removeIf(s -> !s.startsWith(args[args.length - 1]));
		
		returnArguments.sort((s1, s2) -> {
			return s1.compareTo(s2);
		});
		
		return returnArguments;
	}
}
