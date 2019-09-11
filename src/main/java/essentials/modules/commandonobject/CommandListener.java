package essentials.modules.commandonobject;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class CommandListener implements Listener {
	@EventHandler
	private void onBlockClick(PlayerInteractEvent e){
		Player p = e.getPlayer();
		
		if(e.getClickedBlock() == null) return;
		
		String clickedMaterial = e.getClickedBlock().getType().name().toLowerCase();
		if(clickedMaterial.contains("plate")) {
			switch(e.getAction()) {
				case LEFT_CLICK_AIR:
				case LEFT_CLICK_BLOCK:
				case RIGHT_CLICK_AIR:
				case RIGHT_CLICK_BLOCK:
					return;
				default:
					break;
			}
		}
		
		CoBAction action = null;
		switch(e.getAction()) {
			case PHYSICAL:
				break;
			case RIGHT_CLICK_BLOCK:
				if(p.isSneaking())
					action = CoBAction.SNEAK_RIGHT_CLICK;
				else
					action = CoBAction.STAND_RIGHT_CLICK;
				break;
			case LEFT_CLICK_BLOCK:
			default:
				if(p.isSneaking())
					action = CoBAction.SNEAK_LEFT_CLICK;
				else
					action = CoBAction.STAND_LEFT_CLICK;
		}
		
		CommandOnBlock.executeBlock(p, action, e.getClickedBlock().getLocation());
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		CommandOnBlock.clear(e.getBlock().getLocation());
	}
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		CommandOnBlock.loadChunk(event.getChunk());
	}
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		CommandOnBlock.unloadChunk(event.getChunk());
	}
}
