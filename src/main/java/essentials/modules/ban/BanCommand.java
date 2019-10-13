package essentials.modules.ban;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import essentials.utilities.StringUtilities;
import essentials.utilities.player.PlayerUtilities;

public class BanCommand implements CommandExecutor, TabCompleter {
	
	public static BanCommand commands;
	
	static {
		commands = new BanCommand();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 1) return true;
		
		switch (args[0].toLowerCase()) {
			case "ban": { // tempban <Player> <Reason>
				
				if(args.length < 2) break;
				BanManager.banPlayer(PlayerUtilities.getUUID(args[1]), args.length < 3 ? null : StringUtilities.arrayToStringRange(args, 2, args.length));
				//TODO message
				
				break;
			}	
			case "tempban": { // tempban <Player> <Time> <Reason>
				
				if(args.length < 3) break;
				BanManager.banPlayer(PlayerUtilities.getUUID(args[1]), args.length < 4 ? null : StringUtilities.arrayToStringRange(args, 3, args.length), args[2]);
				//TODO message
				
				break;
			}
			case "unban": {
				
				if(args.length < 2) break;
				BanManager.unbanPlayer(PlayerUtilities.getUUID(args[1]));
				System.out.println("unban!"); //TODO message
				
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
