package essentials.warpmanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class warpServer {
	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("warp")){
			if(cmdLabel.equalsIgnoreCase("wa") || cmdLabel.equalsIgnoreCase("warp")){
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("help")){
						sender.sendMessage("/wa list");
						sender.sendMessage("/wa list <Ordner>");
						sender.sendMessage("/wa <File> <Ordner>");
					}else if(args[0].equalsIgnoreCase("list")){
						
					}else{
						
					}
				}else if(args.length == 2){
					if(args[0].equalsIgnoreCase("list")){
						
					}else{
						
					}
				}
			}else if(cmdLabel.equalsIgnoreCase("setwa") || cmdLabel.equalsIgnoreCase("setwarp")){
				if(sender.hasPermission("setwa.use")){
					if(args.length == 1){
						if(args[0].equalsIgnoreCase("help")){
							sender.sendMessage("/setwa <File> <Ordner>");
						}else{
							
						}
					}else if(args.length == 2){
						
					}
				}
			}else if(cmdLabel.equalsIgnoreCase("delwa") || cmdLabel.equalsIgnoreCase("delwarp")){
				if(sender.hasPermission("delwa.use")){
					if(args.length == 1){
						if(args[0].equalsIgnoreCase("help")){
							sender.sendMessage("/delwa <File> <FileOrdner>");
						}else{
							
						}
					}else if(args.length == 2){
						
					}
				}
			}
		}
		
		return true;
	}
}
