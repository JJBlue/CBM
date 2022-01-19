package cbm.utilities.inventory.itemtypes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import cbm.utilities.inventory.InventoryFactory;
import cbm.utilities.inventory.InventoryItem;

public class InventoryItemTypes {
	private InventoryItemTypes() {}

	public static InventoryObjectField<Boolean> createCheckField(String displayName, boolean value) {
		InventoryObjectField<Boolean> item = new InventoryObjectField<>(Material.BLACK_WOOL) {
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
		InventoryObjectField<Integer> item = new InventoryObjectField<>(Material.BROWN_WOOL) {
			@Override
			protected void change(Integer oldValue, Integer value) {
				this.setDisplayName(displayName + value);
			}
		};

		item.setOnClick((event, i) -> {
			switch (event.getClick()) {
				case LEFT:
				case SHIFT_LEFT: {
					var nv = item.getValue() + 1;
					if(nv > max || nv < min) nv = min;
					item.setValue(nv);
					break;
				}
				case RIGHT:
				case SHIFT_RIGHT: {
					var nv = item.getValue() - 1;
					if(nv < min || nv > max) nv = max;
					item.setValue(nv);
					break;
				}
				default:
					break;
			}
			event.setCancelled(true);
		});

		item.setDisplayName(displayName + cur);
		item.setValue(cur);
		return item;
	}
	
	public static InventoryItem createBlockedItem(ItemStack itemStack) {
		InventoryItem item = new InventoryItem(itemStack);
		item.setOnClick((event, i) -> event.setCancelled(true));
		return item;
	}
	
	public static InventoryItem previous(InventoryFactory factory) {
		return previous(factory, new ItemStack(Material.ARROW));
	}
	
	public static InventoryItem previous(InventoryFactory factory, ItemStack itemStack) {
		InventoryItem item = new InventoryItem(itemStack);
		item.setOnClick((event, i) -> {
			event.setCancelled(true);
			factory.previous();
		});
		item.setDisplayName("Previous"); //TODO port to language
		return item;
	}
	
	public static InventoryItem next(InventoryFactory factory) {
		return previous(factory, new ItemStack(Material.ARROW));
	}
	
	public static InventoryItem next(InventoryFactory factory, ItemStack itemStack) {
		InventoryItem item = new InventoryItem(itemStack);
		item.setOnClick((event, i) -> {
			event.setCancelled(true);
			factory.next();
		});
		item.setDisplayName("Next"); //TODO port to language
		return item;
	}
}
