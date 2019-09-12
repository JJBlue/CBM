package essentials.modules.debugstick.blocks;

import essentials.utilities.inventory.InventoryFactory;
import essentials.utilities.inventory.InventoryItem;
import essentials.utilities.inventory.InventoryPage;
import essentials.utilities.inventory.itemtypes.InventoryItemTypes;
import essentials.utilities.inventory.itemtypes.InventoryObjectField;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

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
		player.openInventory(factory.getInventory());
	}
}
