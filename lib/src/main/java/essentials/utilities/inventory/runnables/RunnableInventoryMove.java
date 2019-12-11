package essentials.utilities.inventory.runnables;

import essentials.utilities.inventory.InventoryItem;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public interface RunnableInventoryMove {
	void run(InventoryMoveItemEvent event, InventoryItem item);
}
