package essentials.modules.sudo;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class SudoCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 1) return true;
		
		switch (args[0].toLowerCase()) {
			case "sudo-": { //Only execute command over player/console
				
				break;
			}	
			case "sudo": { //Execute command with your permissions
				
				break;
			}
			case "sudo+": { //Set all players temporaly to operator, WARNING! BUGS & CRITICAL SITUATION
				
				break;
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
