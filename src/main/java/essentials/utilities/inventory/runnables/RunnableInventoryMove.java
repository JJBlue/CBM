package essentials.utilities.inventory.runnables;

import org.bukkit.event.inventory.InventoryMoveItemEvent;

import essentials.utilities.inventory.InventoryItem;

public abstract interface RunnableInventoryMove {
	public abstract void run(InventoryMoveItemEvent event, InventoryItem item);
}
