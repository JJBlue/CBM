package essentials.modules.ban;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import essentials.utilities.player.PlayerUtilities;

public class banCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		switch (args[0].toLowerCase()) {
			case "ban": {
				
				if(args.length < 2) break;
				BanManager.banPlayer(PlayerUtilities.getUUID(args[1]), args.length < 3 ? null : args[2]);
				
				break;
			}	
			case "tempban": {
				
				if(args.length < 3) break;
				BanManager.banPlayer(PlayerUtilities.getUUID(args[1]), args[2], args.length < 4 ? null : args[3]);
				
				break;
			}
			case "unban": {
				
				if(args.length < 2) break;
				BanManager.unbanPlayer(PlayerUtilities.getUUID(args[1]));
				
				break;
			}
			case "checkban": {
				//TODO
			}
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
