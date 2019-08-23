package essentials.inventory.runnables;

import org.bukkit.event.inventory.InventoryEvent;

import essentials.inventory.itemtypes.InventoryObjectField;

public interface RunnableInventoryChangeValue<T> {
	public abstract void run(T oldValue, T newValue, InventoryObjectField<T> item);
}
