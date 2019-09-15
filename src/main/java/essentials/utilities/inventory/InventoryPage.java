package essentials.utilities.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InventoryPage {

	protected Map<Integer, ItemStack> inv = Collections.synchronizedMap(new HashMap<>());

	public InventoryPage() {}

	public InventoryPage(Inventory inventory) {
		fillPage(inventory);
	}

	public void fillPage(Inventory inventory) {
		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack itemStack = inventory.getItem(i);
			if (itemStack == null || itemStack.getType().equals(Material.AIR)) continue;
			addItem(i, new InventoryItem(itemStack));
		}
	}

	public void addItem(ItemStack item) {
		synchronized (inv) {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				if (!inv.containsKey(i)) {
					inv.put(i, item);
					break;
				}
			}
		}
	}

	public void addItem(int pos, ItemStack item) {
		if (item == null) return;
		inv.put(pos, item);
	}

	public void removeItem(ItemStack delete) {
		if (delete == null) return;

		synchronized (inv) {
			int posDelete = -1;

			for (Integer pos : inv.keySet()) {
				ItemStack item = inv.get(pos);

				if (item.equals(delete)) {
					posDelete = pos;
					break;
				}
			}

			if (posDelete > -1)
				inv.remove(posDelete);
		}
	}

	public void removeItem(int pos) {
		inv.remove(pos);
	}

	public ItemStack get(int pos) {
		return inv.get(pos);
	}

	public InventoryItem getInventoryItem(int pos) {
		ItemStack itemStack = inv.get(pos);
		if (itemStack instanceof InventoryItem)
			return (InventoryItem) itemStack;
		return null;
	}

	public void clear() {
		inv.clear();
	}

	public InventoryItem getInventoryItem(ItemStack cursor) {
		synchronized (inv) {
			for (Integer pos : inv.keySet()) {
				ItemStack item = inv.get(pos);

				if (item.equals(cursor) && item instanceof InventoryItem)
					return (InventoryItem) item;
			}
		}

		return null;
	}

	public List<InventoryItem> getInventoryItemEquals(ItemStack clickedStack) {
		List<InventoryItem> list = new LinkedList<>();

		synchronized (inv) {
			for (ItemStack item : inv.values()) {
				if (item.equals(clickedStack) && item instanceof InventoryItem)
					list.add((InventoryItem) item);
			}
		}

		return list;
	}

	public ItemStack getInventoryItemEqualsFirst(ItemStack clickedStack) {
		synchronized (inv) {
			for (ItemStack item : inv.values()) {
				if (item.equals(clickedStack))
					return item;
			}
		}

		return null;
	}

	public void fill(Inventory inventory) {
		for (Integer pos : inv.keySet())
			inventory.setItem(pos, inv.get(pos));
	}

	public Map<Integer, ItemStack> getInv() {
		return inv;
	}

	public void setInv(Map<Integer, ItemStack> inv) {
		this.inv = inv;
	}

	public boolean contains(int pos) {
		return inv.containsKey(pos);
	}
}
