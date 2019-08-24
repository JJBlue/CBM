package essentials.inventory.runnables;

import essentials.inventory.itemtypes.InventoryObjectField;

public interface RunnableInventoryChangeValue<T> {
	public abstract void run(T oldValue, T newValue, InventoryObjectField<T> item);
}
