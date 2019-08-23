package essentials.inventory.itemtypes;

import org.bukkit.Material;

public class InventoryItemTypes {
	private InventoryItemTypes() {}
	
	public static InventoryObjectField<Boolean> createCheckField(boolean value) {
		InventoryObjectField<Boolean> item = new InventoryObjectField<Boolean>(Material.BLACK_WOOL) {
			@Override
			protected void change(Boolean oldValue, Boolean value) {
				if(value)
					this.setType(Material.GREEN_WOOL);
				else
					this.setType(Material.RED_WOOL);
			}
		};
		
		item.setValue(value);
		return item;
	}
}
