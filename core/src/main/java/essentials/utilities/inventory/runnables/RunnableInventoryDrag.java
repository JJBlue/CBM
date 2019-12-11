package essentials.utilities.inventory.runnables;

import essentials.utilities.inventory.InventoryItem;
import org.bukkit.event.inventory.InventoryDragEvent;

public interface RunnableInventoryDrag {
	void run(InventoryDragEvent event, InventoryItem item);
}
