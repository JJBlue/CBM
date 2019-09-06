package essentials.inventory.runnables;

import org.bukkit.event.inventory.InventoryCloseEvent;

public abstract interface RunnableInventoryClose {
	public abstract void run(InventoryCloseEvent event);
}
