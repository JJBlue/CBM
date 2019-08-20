package essentials.warpmanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class warpPlayer {
	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("wap")){
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("help")){
						sender.sendMessage("/wap list");
						sender.sendMessage("/wap <File>");
					}else if(args[0].equalsIgnoreCase("list")){
						
					}else{
						
					}
				}
			}else if(cmd.getName().equalsIgnoreCase("setwap")){
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("help")){
						sender.sendMessage("/setwap <File>");
					}else{
						
					}
				}
			}else if(cmd.getName().equalsIgnoreCase("delwap")){
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("help")){
						sender.sendMessage("/delwap <File>");
					}else{
						
					}
				}
			}
		}
		
	    return true;
	}
}
