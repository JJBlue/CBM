package essentials.inventory.runnables;

import essentials.inventory.InventoryItem;
import org.bukkit.event.inventory.InventoryDragEvent;

public abstract interface RunnableInventoryDrag {
	public abstract void run(InventoryDragEvent event, InventoryItem item);
}
