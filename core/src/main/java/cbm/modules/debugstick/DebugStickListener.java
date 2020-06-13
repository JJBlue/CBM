package cbm.modules.debugstick;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import cbm.modules.debugstick.blocks.DebugStickBlockChanges;
import cbm.modules.debugstick.blocks.DebugStickBlocks;
import cbm.modules.debugstick.entity.DebugStickEntities;
import cbm.modules.debugstick.entity.DebugStickEntityChanges;
import cbm.modules.debugstick.entity.DebugStickEntityInventory;
import cbm.player.PlayerConfig;
import cbm.player.PlayerManager;
import cbm.utilities.chat.ChatUtilities;
import cbm.utilities.permissions.PermissionHelper;

public class DebugStickListener implements Listener {

	/*
	 * PlayerInteractEvent send sometimes doppel, because Object is clickable -> Redstonewire etc...
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void interact(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (player.getGameMode().equals(GameMode.CREATIVE) && player.isOp())
			return; //He could use the normal Debug_Stick
		if (!player.getInventory().getItemInMainHand().getType().equals(Material.DEBUG_STICK)) return;
		if (!player.isOp() && !PermissionHelper.hasPermission(player, "debugStick")) return;

		event.setCancelled(true);

		Block block = event.getClickedBlock();
		PlayerConfig config = PlayerManager.getConfig(player);

		switch (event.getAction()) {
			case LEFT_CLICK_BLOCK:
				if (player.isSneaking()) {
					DebugStickBlocks.openBlockStateEditor(player, block);
					return;
				}

				List<DebugStickBlockChanges> list = DebugStickBlocks.getPossibleBlockStateChanges(block);
				if (list.isEmpty()) break;
				DebugStickBlockChanges debugStickBlockChanges = (DebugStickBlockChanges) config.get("DebugStickBlockChangesCurrent");

				if (debugStickBlockChanges == null)
					debugStickBlockChanges = list.get(0);
				else {
					int i = list.indexOf(debugStickBlockChanges);

					if (i < 0 || i == list.size() - 1)
						debugStickBlockChanges = list.get(0);
					else
						debugStickBlockChanges = list.get(++i);
				}

				config.setTmp("DebugStickBlockChangesCurrent", debugStickBlockChanges);
				ChatUtilities.sendHotbarMessage(player, "Selected: " + debugStickBlockChanges.name());

				break;
			case RIGHT_CLICK_BLOCK:
				debugStickBlockChanges = (DebugStickBlockChanges) config.get("DebugStickBlockChangesCurrent");
				if (debugStickBlockChanges == null) break;

				DebugStickBlocks.setNextBlockState(block, debugStickBlockChanges, !player.isSneaking());
				ChatUtilities.sendHotbarMessage(player, "Set Value to " + DebugStickBlocks.getBlockDataValue(block, debugStickBlockChanges));

				break;
			case LEFT_CLICK_AIR:
				if (!player.isSneaking())
					DebugStickEntityInventory.openInventory(player, player);
				break;
			default:
				break;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void interactEntity(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();

		if (!player.getInventory().getItemInMainHand().getType().equals(Material.DEBUG_STICK)) return;
		if (!player.isOp() && !PermissionHelper.hasPermission(player, "debugStick")) return;

		event.setCancelled(true);
		
		if (player.isSneaking() && entity.isInvulnerable()) {
			entity.setInvulnerable(false);
			ChatUtilities.sendHotbarMessage(player, "Invulnerable is toggled off");
			return;
		} else if(player.isSneaking() && (entity instanceof Minecart || entity instanceof Boat)) {
			DebugStickEntities.openEntityStateEditor(player, entity);
			return;
		}

		PlayerConfig config = PlayerManager.getConfig(player);

		DebugStickEntityChanges debugStickBlockChanges = (DebugStickEntityChanges) config.get("DebugStickEntityChangesCurrent");
		if (debugStickBlockChanges == null) return;
		if (System.currentTimeMillis() - config.getLong("DebugStickEntityChangesTimeout") < 500) return;

		config.setTmp("DebugStickEntityChangesTimeout", System.currentTimeMillis());

		DebugStickEntities.setNextEntityState(entity, debugStickBlockChanges, !player.isSneaking());
		ChatUtilities.sendHotbarMessage(player, "Set Value to " + DebugStickEntities.getEntityStateValue(entity, debugStickBlockChanges));
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void damage(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;

		Player player = (Player) event.getDamager();

		if (!player.getInventory().getItemInMainHand().getType().equals(Material.DEBUG_STICK)) return;
		if (!player.isOp() && !PermissionHelper.hasPermission(player, "debugStick")) return;

		event.setCancelled(true);

		Entity entity = event.getEntity();
		
		if (player.isSneaking()) {
			DebugStickEntities.openEntityStateEditor(player, entity);
			return;
		}

		PlayerConfig config = PlayerManager.getConfig(player);

		List<DebugStickEntityChanges> list = DebugStickEntities.getPossibleEntityStateChanges(entity);
		if (list.isEmpty()) return;
		DebugStickEntityChanges debugStickBlockChanges = (DebugStickEntityChanges) config.get("DebugStickEntityChangesCurrent");

		if (debugStickBlockChanges == null)
			debugStickBlockChanges = list.get(0);
		else {
			int i = list.indexOf(debugStickBlockChanges);

			if (i < 0 || i == list.size() - 1)
				debugStickBlockChanges = list.get(0);
			else
				debugStickBlockChanges = list.get(++i);
		}

		config.setTmp("DebugStickEntityChangesCurrent", debugStickBlockChanges);
		ChatUtilities.sendHotbarMessage(player, "Selected: " + debugStickBlockChanges.name());
	}
	
//	@EventHandler
//    public void onMinecartCollision(VehicleEntityCollisionEvent event) {
//        Entity vehicle = event.getVehicle();
//        ((Minecart) vehicle).setMaxSpeed(0);
//    }
}
