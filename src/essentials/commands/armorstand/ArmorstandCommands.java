package essentials.commands.armorstand;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;

public class ArmorstandCommands implements CommandExecutor, TabCompleter {

	public static final ArmorstandCommands armorstandCommands;
	
	static {
		armorstandCommands = new ArmorstandCommands();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(!sender.hasPermission("all.armorstand")) return true;
		if(!(sender instanceof Player)) return true;
		
		Player p = (Player) sender;
		
		switch(args[0].toLowerCase()) {
			case "near":
				
				ArmorStand armorStand = null;
				
				for(Entity entity : p.getNearbyEntities(20, 20, 20)) {
					if(entity instanceof ArmorStand){
						armorStand = (ArmorStand) entity;
						break;
					}
				}
				
				if(armorStand == null) break;
				
				new ArmorstandInventory(armorStand).openInventory(p);
				
				PlayerConfig config = PlayerManager.getPlayerConfig(p);
				config.set("armorstandEditor", armorStand, false, true);
				
				break;
				
			case "last":
				
				config = PlayerManager.getPlayerConfig(p);
				armorStand = (ArmorStand) config.get("armorstandEditor");
				if(armorStand != null)
					new ArmorstandInventory(armorStand).openInventory(p);
				
				break;
				
			case "lookAt":
				
				break;
		}
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		
		
		return null;
	}

}
