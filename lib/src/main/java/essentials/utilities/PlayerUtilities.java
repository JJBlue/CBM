package essentials.utilities;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtilities {
	public static void addItems(Player player, ItemStack... items) {
		List<ItemStack> fulls = new LinkedList<>();
		
		for(ItemStack itemStack : items) {
			Map<Integer, ItemStack> fi = player.getInventory().addItem(itemStack);
			if(fi != null)
				fulls.addAll(fi.values());
		}
		
		for(ItemStack itemStack : fulls) {
			player.getWorld().dropItem(player.getLocation(), itemStack);
		}
	}
	
	public static void addItems(Player player, List<ItemStack> items) {
		List<ItemStack> fulls = new LinkedList<>();
		
		for(ItemStack itemStack : items) {
			Map<Integer, ItemStack> fi = player.getInventory().addItem(itemStack);
			if(fi != null)
				fulls.addAll(fi.values());
		}
		
		for(ItemStack itemStack : fulls) {
			player.getWorld().dropItem(player.getLocation(), itemStack);
		}
	}
}
