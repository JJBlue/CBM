package essentials.utilities.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
	private InventoryManager() {}

	static Map<Inventory, InventoryFactory> factories = Collections.synchronizedMap(new HashMap<>());

	public static void add(InventoryFactory factory) {
		if (factory == null) return;
		factories.put(factory.getInventory(), factory);
	}

	public static void remove(Inventory inventory) {
		factories.remove(inventory);
	}

	public static InventoryFactory getInventoryFactory(Inventory inventory) {
		return factories.get(inventory);
	}

	public static InventoryPage getInventoryPage(Inventory inventory) {
		InventoryFactory inventoryFactory = factories.get(inventory);
		if (inventory != null) return inventoryFactory.getCurrentInventoryPage();
		return null;
	}

	public static InventoryItem getInventoryItem(Inventory inventory, ItemStack cursor) {
		InventoryPage page = getInventoryPage(inventory);
		if (page == null) return null;
		return page.getInventoryItem(cursor);
	}

	public static InventoryItem getInventoryItem(Inventory inventory, int pos) {
		InventoryPage page = getInventoryPage(inventory);
		if (page == null) return null;
		return page.getInventoryItem(pos);
	}

	public static boolean hasInventory(Inventory inventory) {
		return factories.containsKey(inventory);
	}
}
