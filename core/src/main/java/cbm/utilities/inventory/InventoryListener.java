package cbm.utilities.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

		switch (event.getClick()) {
			case DOUBLE_CLICK:

				ItemStack clickedStack = event.getCurrentItem();

				for (Inventory inv : new Inventory[]{inventory, clickedInventory}) {
					if (inv == null) continue;
					InventoryFactory factory = InventoryManager.getInventoryFactory(inv);
					if (factory == null) continue;

					InventoryPage page = factory.getCurrentInventoryPage();
					if (page == null) continue;

					for (InventoryItem item : page.getInventoryItemEquals(clickedStack)) {
						item.callOnClick(event);
						factory.callOnClick(event, item);
					}
				}

				return;
			case SHIFT_LEFT:
			case SHIFT_RIGHT:

				//Solange wird das event gecanncelled
				if (InventoryManager.hasInventory(clickedInventory) || InventoryManager.hasInventory(event.getWhoClicked().getInventory()))
					event.setCancelled(true);

				//TODO
				return;
			case NUMBER_KEY:

				Inventory playerInv = event.getWhoClicked().getInventory();

				for (Inventory inv : new Inventory[]{playerInv, clickedInventory}) {
					InventoryFactory factory = InventoryManager.getInventoryFactory(inv);
					if (factory == null) continue;

					InventoryPage page = factory.getCurrentInventoryPage();
					if (page == null) continue;

					InventoryItem item = page.getInventoryItem(event.getHotbarButton() - 1);
					if (item == null) {
						factory.callOnClick(event, null);
						continue;
					}
					item.callOnClick(event);
					factory.callOnClick(event, item);
				}

				return;
			default:
				break;
		}

		if (clickedInventory == null) return;
		InventoryFactory factory = InventoryManager.getInventoryFactory(clickedInventory);
		if (factory == null) return;

		InventoryItem item = InventoryManager.getInventoryItem(clickedInventory, event.getSlot());
		if (item == null) {
			factory.callOnClick(event, null);
			return;
		}

		item.callOnClick(event);
		factory.callOnClick(event, item);

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
		if (!InventoryManager.hasInventory(source) && !InventoryManager.hasInventory(destination)) return;

		InventoryItem item = InventoryManager.getInventoryItem(source, event.getItem());
		if (item == null) return;

		item.callOnMove(event);
	}
}
