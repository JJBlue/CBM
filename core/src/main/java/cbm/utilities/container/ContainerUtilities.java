package cbm.utilities.container;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;

public class ContainerUtilities {
	public static Inventory getInventory(Block b) {
		if (b.getState() instanceof Chest)
			return ((Chest) b.getState()).getInventory();
		else if (b.getState() instanceof ShulkerBox)
			return ((ShulkerBox) b.getState()).getInventory();

		return null;
	}
}
