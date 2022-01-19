package cbm.utilities.inventory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryPage {

	protected Map<Integer, ItemStack> inv = new ConcurrentHashMap<>();

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
		ItemStack old = null;
		int pos = 0;
		
		do {
			if(old != null)
				inv.put(pos, old);
			
			pos = inv.keySet().parallelStream().mapToInt(i -> i).max().orElse(0);
			old = inv.put(pos, item);
		} while(old != null);
	}

	public void addItem(int pos, ItemStack item) {
		if (item == null) return;
		inv.put(pos, item);
	}

	public void removeItem(ItemStack delete) {
		if (delete == null) return;
		
		inv.values().removeIf(item -> item.equals(delete));
	}

	public void removeItem(int pos) {
		inv.remove(pos);
	}

	public ItemStack get(int pos) {
		return inv.get(pos);
	}
	
	public int getSize() {
		return inv.size();
	}
	
	public int count(int from, int to) {
		int count = 0;
		
		for(int i = from; i <= to; i++) {
			if(inv.containsKey(i))
				count++;
		}
		
		return count;
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
		return inv.values().parallelStream()
				.filter(item -> item.equals(cursor) && item instanceof InventoryItem)
				.map(item -> (InventoryItem) item)
				.findAny()
				.orElse(null);
	}

	public Collection<InventoryItem> getInventoryItemEquals(ItemStack clickedStack) {
		return inv.values().parallelStream()
				.filter(item -> item.isSimilar(clickedStack) && item instanceof InventoryItem)
				.map(item -> (InventoryItem) item)
				.collect(Collectors.toList());
	}

	public ItemStack getInventoryItemEqualsFirst(ItemStack clickedStack) {
		return inv.values().parallelStream()
				.filter(item -> item.equals(clickedStack))
				.findAny()
				.orElse(null);
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
