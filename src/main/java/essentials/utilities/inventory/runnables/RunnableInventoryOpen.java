package essentials.utilities.inventory.runnables;

import org.bukkit.event.inventory.InventoryOpenEvent;

public abstract interface RunnableInventoryOpen {
	public abstract void run(InventoryOpenEvent event);
}
