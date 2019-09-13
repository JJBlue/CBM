package essentials.modules.warpmanager;

import essentials.utilities.inventory.InventoryFactory;
import essentials.utilities.inventory.InventoryItem;
import essentials.utilities.inventory.InventoryPage;
import essentials.utilities.inventory.itemtypes.InventoryItemTypes;
import essentials.utilities.inventory.itemtypes.InventoryObjectField;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class WarpEditor {
	private WarpEditor() {
	}

	public static void openEditor(Player player, final Warp warp) {
		if (player == null || warp == null) return;

		InventoryFactory factory = new InventoryFactory(Bukkit.createInventory(null, 36));
		InventoryPage page = factory.createFirstPage();

		page.addItem(10, warp.itemStack);

		InventoryObjectField<Boolean> hasPer = InventoryItemTypes.createCheckField("§aRequires Permission", warp.hasPermission);
		hasPer.setOnChangeValue((old, neu, item) -> {
			warp.hasPermission = neu;
			warp.saved = false;
		});
		page.addItem(12, hasPer);

		InventoryObjectField<Boolean> showPer = InventoryItemTypes.createCheckField("§aShow without Permission", warp.showWithoutPermission);
		showPer.setOnChangeValue((old, neu, item) -> {
			warp.showWithoutPermission = neu;
			warp.saved = false;
		});
		page.addItem(13, showPer);

		InventoryObjectField<Boolean> autoLore = InventoryItemTypes.createCheckField("§aUse auto Lore", warp.autoLore);
		autoLore.setOnChangeValue((old, neu, item) -> {
			warp.autoLore = neu;
			warp.saved = false;
		});
		page.addItem(14, autoLore);

		InventoryItem setLoc = new InventoryItem(Material.BROWN_WOOL);
		setLoc.setDisplayName("§aSet new Location");
		setLoc.setOnClick((event, item) -> {
			warp.location = player.getLocation();
			warp.saved = false;
			event.setCancelled(true);
			player.closeInventory();
		});
		List<String> lore = new LinkedList<>();
		lore.add("World: " + player.getWorld().getName());
		lore.add(player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ());
		setLoc.setLore(lore);
		page.addItem(21, setLoc);

		InventoryObjectField<Integer> pos = InventoryItemTypes.createIntField("§aSet Position Chance: ", Integer.MIN_VALUE, Integer.MAX_VALUE, warp.pos);
		pos.setOnChangeValue((old, neu, item) -> {
			warp.pos = neu;
			warp.saved = false;
		});
		page.addItem(22, pos);

		factory.setOnClick((event, item) -> {
			if (event.getSlot() == 10) {
				warp.itemStack = event.getCursor().clone();
				warp.saved = false;
			} else
				event.setCancelled(true);
		});

		InventoryItem barrier = new InventoryItem(Material.BLACK_STAINED_GLASS_PANE);
		barrier.setDisplayName("");

		for (int i = 0; i < 36; i++) {
			if (i == 10 || page.contains(i)) continue;
			page.addItem(i, barrier.clone());
		}

		factory.refreshPage();
		factory.setDeleteOnExit();
		factory.openInventory(player);
	}
}
