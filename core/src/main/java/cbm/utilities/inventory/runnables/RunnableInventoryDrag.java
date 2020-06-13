package cbm.utilities.inventory.runnables;

import org.bukkit.event.inventory.InventoryDragEvent;

import cbm.utilities.inventory.InventoryItem;

public interface RunnableInventoryDrag {
	void run(InventoryDragEvent event, InventoryItem item);
}
