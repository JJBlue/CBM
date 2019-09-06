package essentials.inventory.runnables;

import org.bukkit.event.inventory.InventoryOpenEvent;

public abstract interface RunnableInventoryOpen {
	public abstract void run(InventoryOpenEvent event);
}
