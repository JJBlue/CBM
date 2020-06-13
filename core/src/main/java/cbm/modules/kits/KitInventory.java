package cbm.modules.kits;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import cbm.utilities.inventory.InventoryFactory;
import cbm.utilities.inventory.InventoryItem;
import cbm.utilities.inventory.InventoryPage;
import cbm.utilities.inventory.itemtypes.InventoryItemTypes;

public class KitInventory {
	public static void open(Player player) {
		int size = KitManager.kits.size();
		
		if(size < 45) {
			size = size / 9 + ((size % 9 > 0) ? 1 : 0);
		} else {
			size = 54;
		}
		
		InventoryFactory factory = new InventoryFactory(Bukkit.createInventory(null, size));
		
		int count = 0;
		
		synchronized (KitManager.kits) {
			for(Kit kit : KitManager.kits.values()) {
				int index = count / 45;
				int offset = count % 45;
				
				InventoryPage page = factory.getPage(index);
				if(page == null) {
					page = new InventoryPage();
					factory.addInventoryPage(index, page);
				}
				
				InventoryItem item = new InventoryItem(kit.showItemStack);
				item.setOnClick((event, ii) -> {
					event.setCancelled(true);
					kit.giveKit(player);
				});
				
				page.addItem(offset, item);
				
				if(index > 0 && offset == 0) {
					page.addItem(46, InventoryItemTypes.previous(factory));
					
					InventoryPage previousPage = factory.getPage(index - 1);
					previousPage.addItem(54, InventoryItemTypes.next(factory));
				}
				
				count++;
			}
		}
		
		factory.openInventory(player);
	}
}
