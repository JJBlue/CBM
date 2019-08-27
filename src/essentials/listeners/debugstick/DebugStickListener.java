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

public class DebugStickListener implements Listener {
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void interact(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(player.getGameMode().equals(GameMode.CREATIVE) && player.isOp()) return; //He could use the normal Debug_Stick
		if(!player.getInventory().getItemInMainHand().getType().equals(Material.DEBUG_STICK)) return;
		if(!player.isOp() || !PermissionHelper.hasPermission(player, "debugStick")) return;
		
		event.setCancelled(true);
		
		Block block = event.getClickedBlock();
		
		List<DebugStickBlockChanges> list = DebugStickBlocks.getPossibleBlockStateChanges(block);
		
		for(DebugStickBlockChanges s : list)
			player.sendMessage(s.name());
		player.sendMessage("");
		
		if(!list.isEmpty())
			DebugStickBlocks.setNextBlockState(block, list.get(0), true);
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void interactEntity(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		
		if(!player.getInventory().getItemInMainHand().getType().equals(Material.DEBUG_STICK)) return;
		if(!player.isOp() || !PermissionHelper.hasPermission(player, "debugStick")) return;
		
		event.setCancelled(true);
		
		Entity entity = event.getRightClicked();
		
		//TODO
	}
}
