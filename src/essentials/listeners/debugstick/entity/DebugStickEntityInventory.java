package essentials.listeners.debugstick.entity;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import essentials.inventory.InventoryFactory;
import essentials.inventory.InventoryItem;
import essentials.inventory.InventoryPage;
import essentials.inventory.itemtypes.InventoryItemTypes;
import essentials.inventory.itemtypes.InventoryObjectField;

public class DebugStickEntityInventory {
	/* 
	 * fireticks & remaining air are updated from bukkit -> Value is not refreshed
	 * 
	 */
	
	public static void openInventory(Player player, Entity entity) {
		List<DebugStickEntityChanges> list = DebugStickEntities.getPossibleEntityStateChanges(entity);
		if(list.isEmpty()) return;
		
		InventoryFactory factory = new InventoryFactory(Bukkit.createInventory(null, 54, "Entity (" + entity.getName() + ") Editor"));
		InventoryPage page = factory.createFirstPage();
		
		for(DebugStickEntityChanges debugStickEntitiesChange : list) {
			InventoryItem inventoryItem = null;
			Object value = DebugStickEntities.getEntityStateValue(entity, debugStickEntitiesChange);
			
			if(value instanceof Boolean) {
				InventoryObjectField<Boolean> checkField = InventoryItemTypes.createCheckField(debugStickEntitiesChange.name(), (Boolean) value);
				checkField.setOnChangeValue((old, neu, item) -> {
					DebugStickEntities.setNextEntityState(entity, debugStickEntitiesChange, true);
				});
				inventoryItem = checkField;
			} else {
				inventoryItem = new InventoryItem(Material.LIGHT_GRAY_CONCRETE);
				
				List<String> lore = new LinkedList<>();
				lore.add("Value: " + DebugStickEntities.getEntityStateValue(entity, debugStickEntitiesChange));
				inventoryItem.setLore(lore);
				
				inventoryItem.setOnClick((event, item) -> {
					DebugStickEntities.setNextEntityState(entity, debugStickEntitiesChange, event.getClick().isLeftClick());
					
					lore.clear();
					lore.add("Value: " + DebugStickEntities.getEntityStateValue(entity, debugStickEntitiesChange));
					item.setLore(lore);
					event.setCancelled(true);
				});
			}
			
			
			inventoryItem.setDisplayName(debugStickEntitiesChange.name());
			page.addItem(inventoryItem);
		}
		
		factory.refreshPage();
		player.openInventory(factory.getInventory());
	}
}
