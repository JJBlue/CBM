package essentials.modules.commandonobject;

import java.time.Duration;
import java.time.LocalDateTime;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;

public class CommandListener implements Listener {
	@EventHandler
	private void onBlockClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getClickedBlock() == null) return;

		String clickedMaterial = e.getClickedBlock().getType().name().toLowerCase();
		if (clickedMaterial.contains("plate")) {
			switch (e.getAction()) {
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
		switch (e.getAction()) {
			case PHYSICAL:
				action = CoBAction.EVERYTIME;
				break;
			case RIGHT_CLICK_BLOCK:
				if (p.isSneaking())
					action = CoBAction.SNEAK_RIGHT_CLICK;
				else
					action = CoBAction.STAND_RIGHT_CLICK;
				break;
			case LEFT_CLICK_BLOCK:
			default:
				if (p.isSneaking())
					action = CoBAction.SNEAK_LEFT_CLICK;
				else
					action = CoBAction.STAND_LEFT_CLICK;
		}
		
		Block block = e.getClickedBlock();
		
		PlayerConfig config = PlayerManager.getConfig(p);
		if(
			config.containsLoadedKey("cob_last_location") &&
			config.getString("cob_last_location").equals(block.getX() + ":" + block.getY() + ":" + block.getZ()) &&
			config.containsLoadedKey("con_last_time") &&
			Duration.between(config.getLocalDateTime("con_last_time"), LocalDateTime.now()).toMillis() < 100
		) {
			return;
		}
		
		if(CommandOnBlock.executeBlock(p, action, block.getLocation())) {
			config.setTmp("cob_last_location", block.getX() + ":" + block.getY() + ":" + block.getZ());
			config.setTmp("con_last_time", LocalDateTime.now());
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
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
