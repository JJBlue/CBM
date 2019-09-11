package essentials.utilities.inventory.runnables;

import essentials.utilities.inventory.itemtypes.InventoryObjectField;

public interface RunnableInventoryChangeValue<T> {
	public abstract void run(T oldValue, T newValue, InventoryObjectField<T> item);
}
