package essentials.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryPage {
	
	Map<Integer, InventoryItem> inv = Collections.synchronizedMap(new HashMap<>());
	
	public InventoryPage() {}
	
	public InventoryPage(Inventory inventory) {
		fillPage(inventory);
	}

	public void fillPage(Inventory inventory) {
		for(int i = 0; i < inventory.getSize(); i++) {
			ItemStack itemStack = inventory.getItem(i);
			if(itemStack == null || itemStack.getType().equals(Material.AIR)) continue;
			addInventoryItem(i, new InventoryItem(itemStack));
		}
	}
	
	public void addInventoryItem(InventoryItem item) {
		synchronized (inv) {
			for(int i = 0; i < Integer.MAX_VALUE; i++) {
				if(!inv.containsKey(i)) {
					inv.put(i, item);
					break;
				}
			}
		}
	}
	
	public void addInventoryItem(int pos, InventoryItem item) {
		if(item == null) return;
		inv.put(pos, item);
	}
	
	public void removeInventoryItem(InventoryItem delete) {
		if(delete == null) return;
		
		synchronized (inv) {
			int posDelete = -1;
			
			for(Integer pos : inv.keySet()) {
				InventoryItem item = inv.get(pos);
				
				if(item.equals(delete)) {
					posDelete = pos;
					break;
				}
			}
			
			if(posDelete > -1)
				inv.remove(posDelete);
		}
	}
	
	public void removeInventoryItem(int pos) {
		inv.remove(pos);
	}
	
	public InventoryItem getInventoryItem(int pos) {
		return inv.get(pos);
	}
	
	public InventoryItem getInventoryItem(ItemStack cursor) {
		synchronized (inv) {
			for(Integer pos : inv.keySet()) {
				InventoryItem item = inv.get(pos);
				
				if(item.equals(cursor))
					return item;
			}
		}
		
		return null;
	}
	
	public List<InventoryItem> getInventoryItemEquals(ItemStack clickedStack) {
		List<InventoryItem> list = new LinkedList<>();
		
		synchronized (inv) {
			for(InventoryItem item : inv.values()) {
				if(item.getItemStack().equals(clickedStack))
					list.add(item);
			}
		}
		
		return list;
	}
	
	public InventoryItem getInventoryItemEqualsFirst(ItemStack clickedStack) {
		synchronized (inv) {
			for(InventoryItem item : inv.values()) {
				if(item.getItemStack().equals(clickedStack))
					return item;
			}
		}
		
		return null;
	}
	
	public void fill(Inventory inventory) {
		for(Integer pos : inv.keySet())
			inventory.setItem(pos, inv.get(pos).getItemStack());
	}
}
