package essentials.commands.armorstand;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import essentials.inventory.InventoryFactory;
import essentials.inventory.InventoryPage;

public class ArmorstandCommands implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(!sender.hasPermission("all.armorstand")) return true;
		
		InventoryFactory factory = new InventoryFactory(Bukkit.createInventory(null, 54));
		InventoryPage page = factory.createFirstPage();
		
		
		
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		
		
		return null;
	}

}
