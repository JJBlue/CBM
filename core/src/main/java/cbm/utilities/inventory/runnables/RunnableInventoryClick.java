package cbm.utilities.inventory.runnables;

import org.bukkit.event.inventory.InventoryClickEvent;

import cbm.utilities.inventory.InventoryItem;

public interface RunnableInventoryClick {
	void run(InventoryClickEvent event, InventoryItem item);
}
