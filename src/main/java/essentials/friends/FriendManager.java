package essentials.friends;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendManager {
	private static String suffix = "[Friend]";
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("friend") && sender instanceof Player){
			Player p = (Player) sender;
			
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("help")){
					help(p);
				}else if(args[0].equalsIgnoreCase("list")){
					
				}else if(args[0].equalsIgnoreCase("requests")){
					
				}else if(args[0].equalsIgnoreCase("toggle")){
					
				}else if(args[0].equalsIgnoreCase("show")){
					
				}
			}else if(args.length == 2){
				if(args[0].equalsIgnoreCase("add")){
					
				}else if(args[0].equalsIgnoreCase("remove")){
					
				}else if(args[0].equalsIgnoreCase("accept")){
					
				}else if(args[0].equalsIgnoreCase("deny")){
					
				}
			} else
				help(p);
		}
		
		if (args.length>1) {
		    return true;
		}else{
			return false;
		}
	}
	
	public void help(Player p){
		p.sendMessage("/friend add <Player>");
		p.sendMessage("/friend remove <Player>");
		p.sendMessage("/friend accept <Player>");
		p.sendMessage("/friend deny <Player>");
		p.sendMessage("/friend list");
		p.sendMessage("/friend requests");
		p.sendMessage("/friend toggle");
		p.sendMessage("/friend show");
	}
}
