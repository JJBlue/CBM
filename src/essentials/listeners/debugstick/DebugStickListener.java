package essentials.listeners.debugstick;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import essentials.listeners.debugstick.blocks.DebugStickBlockChanges;
import essentials.listeners.debugstick.blocks.DebugStickBlocks;
import essentials.permissions.PermissionHelper;
import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import essentials.utilities.chat.ChatUtilities;

public class DebugStickListener implements Listener {
	
	/*
	 * TODO:
	 * PlayerInteractEvent send sometimes doppel -> Redstonewire etc...
	 */
	@EventHandler (priority = EventPriority.HIGHEST)
	public void interact(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(player.getGameMode().equals(GameMode.CREATIVE) && player.isOp()) return; //He could use the normal Debug_Stick
		if(!player.getInventory().getItemInMainHand().getType().equals(Material.DEBUG_STICK)) return;
		if(!player.isOp() || !PermissionHelper.hasPermission(player, "debugStick")) return;
		
		event.setCancelled(true);
		
		Block block = event.getClickedBlock();
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		
		switch (event.getAction()) {
			case LEFT_CLICK_BLOCK:
				List<DebugStickBlockChanges> list = DebugStickBlocks.getPossibleBlockStateChanges(block);
				if(list.isEmpty()) break;
				DebugStickBlockChanges debugStickBlockChanges = (DebugStickBlockChanges) config.get("DebugStickBlockChangesCurrent");
				
				if(debugStickBlockChanges == null)
					debugStickBlockChanges = list.get(0);
				else {
					int i = list.indexOf(debugStickBlockChanges);
					
					if(i < 0 || i == list.size() - 1)
						debugStickBlockChanges = list.get(0);
					else
						debugStickBlockChanges = list.get(++i);
				}
				
				config.setTmp("DebugStickBlockChangesCurrent", debugStickBlockChanges);
				ChatUtilities.sendHotbarMessage(player, "Selected: " + debugStickBlockChanges.name());
				
				break;
			case RIGHT_CLICK_BLOCK:
				debugStickBlockChanges = (DebugStickBlockChanges) config.get("DebugStickBlockChangesCurrent");
				if(debugStickBlockChanges == null) break;
				
				DebugStickBlocks.setNextBlockState(block, debugStickBlockChanges, !player.isSneaking());
				ChatUtilities.sendHotbarMessage(player, "Set Value to " + DebugStickBlocks.getBlockDataValue(block, debugStickBlockChanges));
				
				break;
			default:
				break;
		}
	}
	
//	@EventHandler (priority = EventPriority.HIGHEST)
	public void interactEntity(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		
		if(!player.getInventory().getItemInMainHand().getType().equals(Material.DEBUG_STICK)) return;
		if(!player.isOp() || !PermissionHelper.hasPermission(player, "debugStick")) return;
		
		event.setCancelled(true);
		
		Entity entity = event.getRightClicked();
		
		//TODO
	}
}
