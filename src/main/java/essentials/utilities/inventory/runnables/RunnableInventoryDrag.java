package essentials.utilities.inventory.runnables;

import org.bukkit.event.inventory.InventoryDragEvent;

import essentials.utilities.inventory.InventoryItem;

public abstract interface RunnableInventoryDrag {
	public abstract void run(InventoryDragEvent event, InventoryItem item);
}
