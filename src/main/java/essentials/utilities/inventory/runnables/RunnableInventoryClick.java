package essentials.utilities.inventory.runnables;

import essentials.utilities.inventory.InventoryItem;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface RunnableInventoryClick {
	void run(InventoryClickEvent event, InventoryItem item);
}
