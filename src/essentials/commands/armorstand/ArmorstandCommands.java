package essentials.commands.armorstand;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import essentials.permissions.PermissionHelper;
import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;

public class ArmorstandCommands implements CommandExecutor, TabCompleter {

	public static final ArmorstandCommands armorstandCommands;
	
	static {
		armorstandCommands = new ArmorstandCommands();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(!PermissionHelper.hasPermission(sender, "armorstand")) return true;
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
				config.setTmp("armorstandEditor", armorStand);
				
				break;
				
			case "use":

				if(args.length < 2) break;
				
				Entity entity = Bukkit.getEntity(UUID.fromString(args[1]));
				if(!(entity instanceof ArmorStand)) break;
				armorStand = (ArmorStand) entity;
				
				new ArmorstandInventory(armorStand).openInventory(p);
				
				config = PlayerManager.getPlayerConfig(p);
				config.setTmp("armorstandEditor", armorStand);
				
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
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("near");
			returnArguments.add("last");
			returnArguments.add("use");
			
		} else {
			switch (args[0].toLowerCase()) {
				case "use":
					if(!(sender instanceof Player)) break;
					
					for(Entity entity : ((Player) sender).getNearbyEntities(20, 20, 20)) {
						if(entity instanceof ArmorStand)
							returnArguments.add(entity.getUniqueId().toString());
					}
				
				default:
					break;
			}
		}
		
		returnArguments.removeIf(s -> !s.startsWith(args[args.length - 1]));
		
		returnArguments.sort((s1, s2) -> {
			return s1.compareTo(s2);
		});
		
		return returnArguments;
	}

}
