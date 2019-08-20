package essentials.inventory.runnables;

import org.bukkit.event.inventory.InventoryDragEvent;

import essentials.inventory.InventoryItem;

public abstract interface RunnableInventoryDrag {
	public abstract void run(InventoryDragEvent event, InventoryItem item);
}
