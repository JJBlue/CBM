package essentials.modules.kits;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class KitsCommands implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 1) {
			if(!(sender instanceof Player)) return true;
			KitInventory.open((Player) sender);
			return true;
		}
		
		switch (args[0].toLowerCase()) {
			case "give": {
				//give <Kid> (<Player>)
				//TODO
				KitManager.getKit(args[1]).giveKit((Player) sender);
				
				break;
			}
			case "open": {
				if(!(sender instanceof Player)) break;
				KitInventory.open((Player) sender);
				break;
			}
			case "edit": {
				if(args.length < 2) break;
				KitEditor.open((Player) sender, KitManager.getKit(args[1]));
				break;
			}
			case "add": {
				if(args.length < 2 || !(sender instanceof Player)) break;
				Kit kit = new Kit(args[1]);
				KitManager.add(kit);
				KitEditor.open((Player) sender, kit);
				break;
			}
			case "remove" : {
				if(args.length < 2) break;
				KitManager.remove(KitManager.getKit(args[1]));
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
