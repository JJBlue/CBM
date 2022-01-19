package cbm.utilities.container;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;

public class ContainerUtilities {
	public static Inventory getInventory(Block b) {
		if(b.getState() instanceof BlockInventoryHolder holder) 
			return holder.getInventory();
		else if(b.getState() instanceof Container container)
			return container.getInventory();
		else if (b.getState() instanceof Chest chest)
			return chest.getInventory();
		else if (b.getState() instanceof ShulkerBox shulker)
			return shulker.getInventory();
		
		return null;
	}
}
