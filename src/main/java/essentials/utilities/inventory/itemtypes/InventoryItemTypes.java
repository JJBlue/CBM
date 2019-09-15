package essentials.utilities.inventory.itemtypes;

import org.bukkit.Material;

public class InventoryItemTypes {
	private InventoryItemTypes() {}

	public static InventoryObjectField<Boolean> createCheckField(String displayName, boolean value) {
		InventoryObjectField<Boolean> item = new InventoryObjectField<Boolean>(Material.BLACK_WOOL) {
			@Override
			protected void change(Boolean oldValue, Boolean value) {
				if (value)
					this.setType(Material.GREEN_WOOL);
				else
					this.setType(Material.RED_WOOL);
			}
		};

		item.setOnClick((event, i) -> {
			item.setValue(!item.getValue());
			event.setCancelled(true);
		});

		item.setDisplayName(displayName);
		item.setValue(value);
		return item;
	}

	public static InventoryObjectField<Integer> createIntField(final String displayName, int min, int max, int cur) {
		InventoryObjectField<Integer> item = new InventoryObjectField<Integer>(Material.BROWN_WOOL) {
			@Override
			protected void change(Integer oldValue, Integer value) {
				this.setDisplayName(displayName + value);
			}
		};

		item.setOnClick((event, i) -> {
			switch (event.getClick()) {
				case LEFT:
				case SHIFT_LEFT:
					item.setValue(item.getValue() + 1);
					break;
				case RIGHT:
				case SHIFT_RIGHT:
					item.setValue(item.getValue() - 1);
					break;
				default:
					break;
			}
			event.setCancelled(true);
		});

		item.setDisplayName(displayName + cur);
		item.setValue(cur);
		return item;
	}
}
