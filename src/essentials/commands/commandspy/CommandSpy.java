package essentials.commands.commandspy;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;

public class CommandSpy implements CommandExecutor, TabCompleter {
	public static final CommandSpy commandSpy;
	
	static {
		commandSpy = new CommandSpy();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(args.length < 1 || !(sender instanceof Player)) return true;
		
		Player player = (Player) sender;
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		
		switch (args[0].toLowerCase()) {
			case "value":
				
				if(args.length < 2) break;
				
				if(args[1].toLowerCase().equals("false")) {
					config.set("commandSpy", -1);
					sender.sendMessage("§eCommand spy toggled to §4False");
				} else {
					try {
						config.set("commandSpy", Integer.valueOf(args[1]));
						sender.sendMessage("§eCommand spy toggled to §6" +Integer.valueOf(args[1]));
					} catch (NumberFormatException e) {
						sender.sendMessage("§4NumberFormatException");
					}
				}
				
				break;
	
			case "operator":
				
				boolean value = !config.getBoolean("commandSpyOperator");
				config.set("commandSpyOperator", value);
				sender.sendMessage("§eCommand spy toggled to §6" + value);
				
				break;
		}
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("value");
			returnArguments.add("operator");
			
		} else {
			switch (args[0]) {
				case "value":
					if(args.length == 2) {
						returnArguments.add("0");
						returnArguments.add("1");
						returnArguments.add("2");
						returnArguments.add("3");
						returnArguments.add("4");
						returnArguments.add("5");
						returnArguments.add("6");
						returnArguments.add("7");
						returnArguments.add("8");
						returnArguments.add("9");
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