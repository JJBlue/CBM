package essentials.money;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import essentials.language.LanguageConfig;

public class MoneyCommands implements CommandExecutor, TabCompleter {
	
	public final static MoneyCommands moneyCommands;
	
	static {
		moneyCommands = new MoneyCommands();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(args.length < 1) return true;
		
		switch (args[0].toLowerCase()) {
			case "add":
				
				//add <Value> (<Player>)
				if(args.length < 2) break;
				
				try {
					long value = Long.parseLong(args[1]);
					
					Player player = null;
					if(args.length == 2) {
						if(sender instanceof Player) player = (Player) sender;
					} else
						player = Bukkit.getPlayer(args[2]);
					
					if(player == null) {
						LanguageConfig.sendMessage(sender, "error.playerIsMissing");
						break;
					}
					
					MoneyManager.addMoney(player.getUniqueId(), value);
					LanguageConfig.sendMessage(sender, "money.addMoney", player.getName(), value + "", MoneyManager.getMoney(player.getUniqueId()) + "");
					
				} catch (NumberFormatException e) {
					LanguageConfig.sendMessage(sender, "error.NumberFormatException");
				}
				
				break;
				
			case "transfer":
				
				//transfer <value> (<fromPlayer>) <toPlayer>
				if(args.length < 2) break;
				
				try {
					long value = Long.parseLong(args[1]);
					
					Player fromPlayer = null;
					Player toPlayer = null;
					
					if(args.length == 3) {
						if(sender instanceof Player) {
							fromPlayer = (Player) sender;
							toPlayer = Bukkit.getPlayer(args[2]);
						}
					} else {
						fromPlayer = Bukkit.getPlayer(args[2]);
						toPlayer = Bukkit.getPlayer(args[3]);
					}
					
					if(toPlayer == null || fromPlayer == null) {
						LanguageConfig.sendMessage(sender, "error.playerIsMissing");
						break;
					}
					
					if(MoneyManager.removeMoney(fromPlayer.getUniqueId(), value, true)) {
						MoneyManager.setMoney(toPlayer.getUniqueId(), value);
						LanguageConfig.sendMessage(sender, "money.addMoney", fromPlayer.getName(), toPlayer.getName(), value + "");
					}
					
					LanguageConfig.sendMessage(sender, "money.couldNotBeTransfered");
					
				} catch (NumberFormatException e) {
					LanguageConfig.sendMessage(sender, "error.NumberFormatException");
				}
				
				break;

			case "set":
				
				if(args.length < 2) break;
				
				try {
					long value = Long.parseLong(args[1]);
					
					Player player = null;
					if(args.length == 2) {
						if(sender instanceof Player) player = (Player) sender;
					} else
						player = Bukkit.getPlayer(args[2]);
					
					if(player == null) {
						LanguageConfig.sendMessage(sender, "error.playerIsMissing");
						break;
					}
					
					MoneyManager.setMoney(player.getUniqueId(), value);
					LanguageConfig.sendMessage(sender, "money.setMoney", player.getName(), value + "");
					
				} catch (NumberFormatException e) {
					LanguageConfig.sendMessage(sender, "error.NumberFormatException");
				}
				
				break;

			case "remove":
				
				if(args.length < 2) break;
				
				try {
					long value = Long.parseLong(args[1]);
					
					Player player = null;
					if(args.length == 2) {
						if(sender instanceof Player) player = (Player) sender;
					} else
						player = Bukkit.getPlayer(args[2]);
					
					if(player == null) {
						LanguageConfig.sendMessage(sender, "error.playerIsMissing");
						break;
					}
					
					MoneyManager.removeMoney(player.getUniqueId(), value);
					LanguageConfig.sendMessage(sender, "money.removeMoney", player.getName(), value + "", MoneyManager.getMoney(player.getUniqueId()) + "");
					
				} catch (NumberFormatException e) {
					LanguageConfig.sendMessage(sender, "error.NumberFormatException");
				}

				break;
				
			case "get":
				
				if(args.length == 1) {
					if(sender instanceof Player)
						LanguageConfig.sendMessage(sender, "money.balance", ((Player) sender).getName(), MoneyManager.getMoney(((Player) sender).getUniqueId()) + "");
				} else {
					Player player = Bukkit.getPlayer(args[1]);
					
					if(player == null) {
						LanguageConfig.sendMessage(sender, "error.playerIsMissing"); 
						break;
					}
					
					LanguageConfig.sendMessage(sender, "money.balance", player.getName(), MoneyManager.getMoney(player.getUniqueId()) + "");
				}
				
				break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("add");
			returnArguments.add("transfer");
			returnArguments.add("set");
			returnArguments.add("remove");
			returnArguments.add("get");

		} else {
			switch (args[0]) {
				case "add":
				case "set":
				case "remove":
					
					if(args.length == 2)
						returnArguments.add("<Value>");
					else if(args.length == 3) {
						for (Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					}
					
					break;
					
				case "transfer":
					
					if(args.length == 2)
						returnArguments.add("<Value>");
					else if(args.length == 3 || args.length == 4) {
						for (Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					}
					
					break;
				
				case "get":
					
					if(args.length == 2) {
						for (Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					}
					
					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
