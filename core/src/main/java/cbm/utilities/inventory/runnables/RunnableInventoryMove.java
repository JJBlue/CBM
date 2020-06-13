package cbm.utilities.inventory.runnables;

import org.bukkit.event.inventory.InventoryMoveItemEvent;

import cbm.utilities.inventory.InventoryItem;

public interface RunnableInventoryMove {
	void run(InventoryMoveItemEvent event, InventoryItem item);
}
