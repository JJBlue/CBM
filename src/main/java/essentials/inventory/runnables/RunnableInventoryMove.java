package essentials.inventory.runnables;

import essentials.inventory.InventoryItem;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public abstract interface RunnableInventoryMove {
	public abstract void run(InventoryMoveItemEvent event, InventoryItem item);
}
