package cbm.modules.debugstick.blocks;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import cbm.utilities.inventory.InventoryFactory;
import cbm.utilities.inventory.InventoryItem;
import cbm.utilities.inventory.InventoryPage;
import cbm.utilities.inventory.itemtypes.InventoryItemTypes;
import cbm.utilities.inventory.itemtypes.InventoryObjectField;

public class DebugStickBlockInventory {
	public static void openInventory(Player player, Block block) {
		List<DebugStickBlockChanges> list = DebugStickBlocks.getPossibleBlockStateChanges(block);
		if (list.isEmpty()) return;

		InventoryFactory factory = new InventoryFactory(Bukkit.createInventory(null, 54, "Block Editor"));
		InventoryPage page = factory.createFirstPage();

		for (DebugStickBlockChanges debugStickBlockChanges : list) {
			InventoryItem inventoryItem = null;
			Object value = DebugStickBlocks.getBlockDataValue(block, debugStickBlockChanges);

			if (value instanceof Boolean) {
				InventoryObjectField<Boolean> checkField = InventoryItemTypes.createCheckField(debugStickBlockChanges.name(), (Boolean) value);
				checkField.setOnChangeValue((old, neu, item) -> DebugStickBlocks.setNextBlockState(block, debugStickBlockChanges, true));
				inventoryItem = checkField;
			} else {
				inventoryItem = new InventoryItem(Material.LIGHT_GRAY_CONCRETE);

				List<String> lore = new LinkedList<>();
				lore.add("Value: " + DebugStickBlocks.getBlockDataValue(block, debugStickBlockChanges));
				inventoryItem.setLore(lore);

				inventoryItem.setOnClick((event, item) -> {
					DebugStickBlocks.setNextBlockState(block, debugStickBlockChanges, event.getClick().isLeftClick());

					lore.clear();
					lore.add("Value: " + DebugStickBlocks.getBlockDataValue(block, debugStickBlockChanges));
					item.setLore(lore);
					event.setCancelled(true);
				});
			}


			inventoryItem.setDisplayName(debugStickBlockChanges.name());
			page.addItem(inventoryItem);
		}

		factory.refreshPage();
		factory.openInventory(player);
	}
}
