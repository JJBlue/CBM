package essentials.server;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class money {
	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		switch (args[0].toLowerCase()) {
			case "help":
				sender.sendMessage("/money [add/remove/set] <Player> <Value>");
				sender.sendMessage("/money get <Player>");
				break;
				
			case "add":
				
				break;
				
			case "remove":
				
				break;
				
			case "set":
				
				break;
				
			case "transfer":
				
				break;
		}
		
	    return true;
	}
}
