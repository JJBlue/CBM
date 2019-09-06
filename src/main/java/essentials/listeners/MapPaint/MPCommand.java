package essentials.listeners.MapPaint;

import essentials.config.MainConfig;
import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MPCommand implements CommandExecutor, TabCompleter {
	
	public static MPCommand mpcommand;
	
	static {
		mpcommand = new MPCommand();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(args.length < 1) return true;
		
		switch (args[0].toLowerCase()) {
			case "add":
				
				if(args.length < 2) return true;
				if(!(sender instanceof Player)) break;
				
				PlayerConfig config = PlayerManager.getPlayerConfig((Player) sender);
				config.setTmp("mapPaintImage", args[1]);
				sender.sendMessage("§6Click on a Block");
				
				break;
				
			case "set":
				
				if(args.length < 6) return false;
				
				try {
					int id = Integer.parseInt(args[1]);
					String pfad = args[2];
					String filename = args[3];
					int x = Integer.parseInt(args[4]);
					int y = Integer.parseInt(args[5]);
					
					LoadMapPaint.setMapPaint(id, pfad, filename, x, y);
					sender.sendMessage("§6Set MapID " + id + " to (" + pfad + "," + filename + "," + x + "," + y + ")");
				} catch (NumberFormatException e) {
					sender.sendMessage("§4Error");
				}
				
				break;
				
			case "remove":
				
				try {
					LoadMapPaint.removeID(Integer.parseInt(args[1]));
					sender.sendMessage("§6Removed MapID " + args[1]);
				} catch (NumberFormatException e) {
					sender.sendMessage("§4Error");
				}
				
				break;
	
			default:
				break;
		}
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("add");
			returnArguments.add("set");
			returnArguments.add("remove");
			
		} else {
			switch (args[0]) {
				case "add":
				
					if(args.length == 2) {
						File dir = new File(MainConfig.getDataFolder() + "picture");
						
						for(File file : dir.listFiles()) {
							if(file.isDirectory()) continue;
							
							returnArguments.add(file.getName());
						}
					}
					else if(args.length == 3) returnArguments.add("<X>");
					else if(args.length == 4) returnArguments.add("<Y>");
				
				case "set":
					
					switch (args.length) {
						case 2:
							returnArguments.add("<MapID>");
							break;
						case 3:
							returnArguments.add("<pfad>");
							break;
						case 4:
							returnArguments.add("<filename>");
							break;
						case 5:
							returnArguments.add("<x>");
							break;
						case 6:
							returnArguments.add("<y>");
							break;
					}
					
				case "remove":
					
					if(args.length == 2)
						returnArguments.add("<MapID>");
			}
		}
		
		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		
		returnArguments.sort((s1, s2) -> {
			return s1.compareTo(s2);
		});
		
		return returnArguments;
	}

}
