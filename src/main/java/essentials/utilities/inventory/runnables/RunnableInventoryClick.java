package essentials.utilities.inventory.runnables;

import org.bukkit.event.inventory.InventoryClickEvent;

import essentials.utilities.inventory.InventoryItem;

public abstract interface RunnableInventoryClick {
	public abstract void run(InventoryClickEvent event, InventoryItem item);
}
