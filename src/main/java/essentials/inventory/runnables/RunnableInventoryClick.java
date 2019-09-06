package essentials.inventory.runnables;

import essentials.inventory.InventoryItem;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract interface RunnableInventoryClick {
	public abstract void run(InventoryClickEvent event, InventoryItem item);
}
