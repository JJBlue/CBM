package essentials.warpmanager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class warpServer {
	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		Player p = null;
		if(sender instanceof Player)
			p = (Player) sender;
		
		switch (args[0].toLowerCase()) {
			case "warp":
				
				if(args.length == 1)
					WarpManager.openInventory(p);
				else if(args.length == 2)
					WarpManager.teleport(p, args[1]);
				else if(args.length == 3)
					WarpManager.teleport(Bukkit.getPlayer(args[2]), args[1]);
				
				break;
				
			case "setwarp":
				
				if(args.length < 2 || p == null) break;
				WarpManager.setWarp(args[1], p.getLocation());
				
				break;
				
			case "delwarp":

				if(args.length < 2 || p == null) break;
				WarpManager.setWarp(args[1], p.getLocation());
				
				break;
				
		}
		
		return true;
	}
}
