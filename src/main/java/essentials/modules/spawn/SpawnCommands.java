package essentials.modules.spawn;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import essentials.language.LanguageConfig;

public class SpawnCommands implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 1) return true;
		
		switch (args[0].toLowerCase()) {
			case "spawn":
				
				if(args.length == 1) {
					if(!(sender instanceof Player)) break;
					
					SpawnManager.teleportToSpawn((Player) sender);
				} else if(args.length == 2) {
					Player player = Bukkit.getPlayer(args[1]);
					if(player == null) break;
					
					SpawnManager.teleportToSpawn(player);
				} else {
					Player player = Bukkit.getPlayer(args[1]);
					if(player == null) break;
					
					SpawnManager.teleportToSpawn(player, args[2]);
				}
				
				break;
				
			case "setspawn":
				
//				SpawnManager.setSpawn(id, location);
				LanguageConfig.sendMessage(sender, "spawn.set-spawn");
				
				break;
				
			case "delspawn":
				
				LanguageConfig.sendMessage(sender, "spawn.del-spawn");
				
				break;
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
