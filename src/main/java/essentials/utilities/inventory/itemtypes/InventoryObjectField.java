package essentials.utilities.inventory.itemtypes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import essentials.utilities.inventory.InventoryItem;
import essentials.utilities.inventory.runnables.RunnableInventoryChangeValue;

public abstract class InventoryObjectField<T> extends InventoryItem {
	protected T value;
	
	private RunnableInventoryChangeValue<T> onChangeValue;

	public InventoryObjectField(ItemStack itemStack) {
		super(itemStack);
	}

	public InventoryObjectField(Material type, int amount) {
		super(type, amount);
	}

	public InventoryObjectField(Material type) {
		super(type);
	}
	
	protected abstract void change(T oldValue, T newValue);
	
	public void setValue(T value) {
		T oldValue = this.value;
		this.value = value;
		change(oldValue, value);
		
		if(onChangeValue != null)
			onChangeValue.run(oldValue, value, this);
	}
	
	public T getValue() {
		return value;
	}

	public RunnableInventoryChangeValue<T> getOnChangeValue() {
		return onChangeValue;
	}

	public void setOnChangeValue(RunnableInventoryChangeValue<T> onChangeValue) {
		this.onChangeValue = onChangeValue;
	}
}
