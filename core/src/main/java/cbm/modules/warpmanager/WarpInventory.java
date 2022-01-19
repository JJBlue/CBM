package cbm.modules.warpmanager;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import cbm.utilities.inventory.InventoryFactory;
import cbm.utilities.inventory.InventoryItem;
import cbm.utilities.inventory.InventoryManager;
import cbm.utilities.inventory.InventoryPage;

public class WarpInventory extends InventoryFactory {
	int curWarpsLoaded = 0;
	Player player;

	public WarpInventory(Player player, Inventory inventory) {
		super(inventory);
		this.player = player;
	}

	public static void openNewInventory(Player player) {
		if (player == null) return;

		WarpInventory factory = new WarpInventory(player, null);
		factory.addNewPage();

		int size = factory.getCurrentInventoryPage().getInv().size();
		size = size >= 45 ? 54 : roundDoubleChestInventory(size);

		factory.inventory = Bukkit.createInventory(null, size, "§6Warps");
		InventoryManager.add(factory);

		factory.refreshPage();
		factory.openInventory(player);

		factory.setDeleteOnExit();
		factory.setOnClick((event, item) -> event.setCancelled(true));
		factory.setOnMove((event, item) -> event.setCancelled(true));
	}

	private static int roundDoubleChestInventory(int size) {
		if (size < 9) return 9;
		else if (size < 18) return 18;
		else if (size < 27) return 27;
		else if (size < 36) return 36;
		else if (size < 45) return 45;
		return 54;
	}

	private InventoryItem page(final boolean nextOrBevor) {
		InventoryItem inventoryItem = new InventoryItem(nextOrBevor ? new ItemStack(Material.GREEN_STAINED_GLASS_PANE) : new ItemStack(Material.RED_STAINED_GLASS_PANE));

		inventoryItem.setDisplayName(nextOrBevor ? "Next" : "Previous");
		inventoryItem.setOnClick((event, item) -> {
			if (nextOrBevor)
				next();
			else
				previous();

			event.setCancelled(true);
		});

		return inventoryItem;
	}

	private static InventoryItem getWarpItem(final Warp warp) {
		ItemStack warpItemStack = warp.getResultItemStack();
		if (warpItemStack != null) {
			switch (warpItemStack.getType()) {
				case AIR:
				case CAVE_AIR:
				case VOID_AIR:
					warpItemStack = null;
					break;
				default: break;
			}
		}
		InventoryItem inventoryItem = new InventoryItem(warpItemStack != null ? warpItemStack : new ItemStack(Material.WHITE_WOOL));

		inventoryItem.setDisplayName(warp.getName());
		inventoryItem.setOnClick((event, item) -> {
			WarpManager.teleport(event.getWhoClicked(), warp);
			event.setCancelled(true);
		});

		return inventoryItem;
	}

	public synchronized void addNewPage() {
		List<Warp> warps = new LinkedList<>();

		while (warps.size() < 45) {
			Collection<Warp> tmp = WarpManager.getWarps(curWarpsLoaded, 45);

			for (Warp warp : tmp) {
				curWarpsLoaded++;
				String per = warp.getPermission();
				if (warp.hasPermission && per != null && !player.hasPermission(per)) continue;
				warps.add(warp);
				if (warps.size() >= 45) break;
			}

			if (tmp.size() < 45) break;
		}

		InventoryPage inventoryPage = new InventoryPage();

		for (Warp warp : warps)
			inventoryPage.addItem(getWarpItem(warp));

		if (warps.size() >= 45)
			inventoryPage.addItem(53, page(true));

		if (pages.size() != 0)
			inventoryPage.addItem(53 - 8, page(false));

		addInventoryPage(inventoryPage);
	}

	@Override
	public synchronized void next() {
		if (getCurrentPage() == pages.size() - 1)
			addNewPage();

		super.next();
	}
}
