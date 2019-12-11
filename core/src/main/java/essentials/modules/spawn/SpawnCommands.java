package essentials.modules.spawn;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import essentials.language.LanguageConfig;

public class SpawnCommands implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 1) return true;
		
		switch (args[0].toLowerCase()) {
			case "spawn":
				
				if(args.length == 1) {
					if(!(sender instanceof Player)) break;
					
					SpawnManager.teleportToSpawn((Player) sender);
					LanguageConfig.sendMessage(sender, "spawn.teleport");
					
				} else if(args.length == 2) {
					Player player = Bukkit.getPlayer(args[1]);
					if(player == null) break;
					
					SpawnManager.teleportToSpawn(player);
					LanguageConfig.sendMessage(sender, "spawn.teleport");
					
				} else if(args.length >= 3) {
					Player player = Bukkit.getPlayer(args[1]);
					if(player == null) break;
					
					SpawnManager.teleportToSpawn(player, args[2]);
					LanguageConfig.sendMessage(sender, "spawn.teleport-to", args[2]);
				}
				
				break;
				
			case "setspawn":
				
				if(!(sender instanceof Player)) break;
				
				if(args.length == 1) {
					
					boolean value = SpawnManager.setSpawn(0, "default", ((Player) sender).getLocation());
					if(value)
						LanguageConfig.sendMessage(sender, "spawn.set-spawn", "0", "default");
					else
						LanguageConfig.sendMessage(sender, "text.problem-detected");
					
				} else if(args.length == 2) {
					
					boolean value = SpawnManager.setSpawn(0, args[1], ((Player) sender).getLocation());
					if(value)
						LanguageConfig.sendMessage(sender, "spawn.set-spawn", "0", args[1]);
					else
						LanguageConfig.sendMessage(sender, "text.problem-detected");
					
				} else if(args.length >= 3) {
					try {
						boolean value = SpawnManager.setSpawn(Integer.parseInt(args[1]), args[2], ((Player) sender).getLocation());
						if(value)
							LanguageConfig.sendMessage(sender, "spawn.set-spawn", args[1], args[2]);
						else
							LanguageConfig.sendMessage(sender, "text.problem-detected");
					} catch (NumberFormatException e) {
						LanguageConfig.sendMessage(sender, "error.NumberFormatException");
					}
				}
				
				break;
				
			case "delspawn":
				
				if(args.length < 2) break;
				
				boolean value = SpawnManager.deleteSpawn(args[1]);
				if(value)
					LanguageConfig.sendMessage(sender, "spawn.del-spawn", args[1]);
				else
					LanguageConfig.sendMessage(sender, "text.problem-detected");
				
				break;
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("spawn");
			returnArguments.add("setspawn");
			returnArguments.add("delspawn");

		} else {
			switch (args[0]) {
				case "spawn":
					if(args.length == 2) {
						for(Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					} else if(args.length == 3)
						returnArguments.add("<Name or ID>");
					
					break;
					
				case "setspawn":
					
					if(args.length == 2) {
						returnArguments.add("<Name>");
						returnArguments.add("<ID>");
					} else if(args.length == 3)
						returnArguments.add("<Name>");
					
					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
