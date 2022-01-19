package cbm.utilities.inventory;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import cbm.utilities.inventory.runnables.RunnableInventoryClose;
import cbm.utilities.inventory.runnables.RunnableInventoryOpen;

public class InventoryListener implements Listener {
	@EventHandler
	public void close(InventoryCloseEvent event) {
		Inventory inventory = event.getInventory();
		if (inventory == null) return;

		InventoryFactory factory = InventoryManager.getInventoryFactory(inventory);
		if (factory == null || !factory.deleteOnExit) return;

		RunnableInventoryClose runnable = factory.getOnClose();
		if (runnable != null) runnable.run(event);

		if (inventory.getViewers().isEmpty())
			InventoryManager.remove(inventory);
	}

	@EventHandler
	public void open(InventoryOpenEvent event) {
		Inventory inventory = event.getInventory();
		if (inventory == null) return;

		InventoryFactory factory = InventoryManager.getInventoryFactory(inventory);
		if (factory == null) return;

		RunnableInventoryOpen runnable = factory.getOnOpen();
		if (runnable != null) runnable.run(event);
	}

	@EventHandler
	public void click(InventoryClickEvent event) {
		if (event.getSlot() < 0) return;

		Inventory inventory = event.getInventory();
		Inventory clickedInventory = event.getClickedInventory();
		Inventory topInventory = event.getWhoClicked().getOpenInventory().getTopInventory();
		Inventory bottomInventory = event.getWhoClicked().getOpenInventory().getBottomInventory();
		
		switch (event.getClick()) {
			case DOUBLE_CLICK: {
				var inventories = Stream.of(inventory, clickedInventory, topInventory, bottomInventory)
						.distinct()
						.filter(inv -> inv != null)
						.collect(Collectors.toList());
				
				for (Inventory inv : inventories) {
					if (inv == null) continue;
					
					InventoryFactory factory = InventoryManager.getInventoryFactory(inv);
					if (factory == null) continue;

					InventoryPage page = factory.getCurrentInventoryPage();
					if (page == null) continue;

					for (InventoryItem item : page.getInventoryItemEquals(event.getCurrentItem())) {
						item.callOnClick(event);
						factory.callOnClick(event, item);
					}
				}

				return;
			}
			case SHIFT_LEFT:
			case SHIFT_RIGHT: {
				
				Inventory destination_inv = (clickedInventory == topInventory ? bottomInventory : topInventory);
				
				InventoryFactory factory = InventoryManager.getInventoryFactory(destination_inv);
				if (factory == null) break;
				
				InventoryPage page = factory.getCurrentInventoryPage();
				if (page == null) break;
				
				boolean found = false;
				for (InventoryItem item : page.getInventoryItemEquals(event.getCurrentItem())) {
					found = true;
					item.callOnClick(event);
					factory.callOnClick(event, item);
				}
				
				if(!found)
					factory.callOnClick(event, null);
				
				break;
			}
			case NUMBER_KEY: {
				Inventory destination_inv = event.getWhoClicked().getOpenInventory().getBottomInventory();
				
				InventoryFactory factory = InventoryManager.getInventoryFactory(destination_inv);
				if (factory == null) break;
				
				InventoryItem item = InventoryManager.getInventoryItem(clickedInventory, event.getHotbarButton() - 1);
				factory.callOnClick(event, item);
				if (item == null) break;
				
				item.callOnClick(event);
				
				break;
			}
			default: break;
		}
		
		if (clickedInventory == null) return;
		
		InventoryFactory factory = InventoryManager.getInventoryFactory(clickedInventory);
		if (factory == null) return;
		
		InventoryItem item = InventoryManager.getInventoryItem(clickedInventory, event.getSlot());
		factory.callOnClick(event, item);
		if (item == null) return;
		
		item.callOnClick(event);
			
		if (!item.equals(event.getCurrentItem()))
			event.setCurrentItem(item);
	}

	@EventHandler
	public void drag(InventoryDragEvent event) {
		Inventory inventory = event.getInventory();
		if (inventory == null) return;
		if (!InventoryManager.hasInventory(inventory)) return;

		InventoryItem item = InventoryManager.getInventoryItem(inventory, event.getCursor());
		if (item == null) return;

		item.callOnDrag(event);
	}

	@EventHandler
	public void move(InventoryMoveItemEvent event) {
		Inventory source = event.getSource();
		Inventory destination = event.getDestination();
		if (source == null || destination == null) return;
		
		boolean is_source = InventoryManager.hasInventory(source);
		boolean is_destination = InventoryManager.hasInventory(source);
		
		if (!is_source && !is_destination) return;
		
		InventoryItem item = InventoryManager.getInventoryItem(source, event.getItem());
		
		if(is_source) {
			InventoryFactory factory = InventoryManager.getInventoryFactory(source);
			factory.callOnMove(event, item);
		}
		
		if(is_destination) {
			InventoryFactory factory = InventoryManager.getInventoryFactory(destination);
			factory.callOnMove(event, item);
		}
		
		if (item == null) return;
		item.callOnMove(event);
	}
}
