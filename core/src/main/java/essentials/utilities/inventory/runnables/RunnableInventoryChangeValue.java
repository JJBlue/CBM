package essentials.utilities.inventory.runnables;

import essentials.utilities.inventory.itemtypes.InventoryObjectField;

public interface RunnableInventoryChangeValue<T> {
	void run(T oldValue, T newValue, InventoryObjectField<T> item);
}
