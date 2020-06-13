package cbm.utilities.inventory.runnables;

import cbm.utilities.inventory.itemtypes.InventoryObjectField;

public interface RunnableInventoryChangeValue<T> {
	void run(T oldValue, T newValue, InventoryObjectField<T> item);
}
