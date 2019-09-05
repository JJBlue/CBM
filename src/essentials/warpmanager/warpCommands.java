package essentials.warpmanager;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class warpCommands implements CommandExecutor, TabCompleter {
	public final static warpCommands commands;
	
	static {
		commands = new warpCommands();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
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
				
				Warp warp = new Warp(args[1]);
				warp.setLocation(p.getLocation());
				
				WarpManager.addWarp(warp);
				
				break;
				
			case "delwarp":

				if(args.length < 2 || p == null) break;
				WarpManager.deleteWarp(args[1]);
				
				break;
				
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}
}
