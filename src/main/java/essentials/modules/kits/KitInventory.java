package essentials.modules.kits;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import essentials.utilities.inventory.InventoryFactory;
import essentials.utilities.inventory.InventoryItem;
import essentials.utilities.inventory.InventoryPage;

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
				
				page.addItem(count % 45, item);
				
				count++;
			}
		}
		
		factory.openInventory(player);
	}
}
