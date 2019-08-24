package essentials.commands.commandonobject;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CommandListener implements Listener{
	
	@SuppressWarnings("unchecked")
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
				case PHYSICAL:
					break;
			}
		}
		
		FileConfiguration fileConf = CommandDruckplatten.CDConf(p.getWorld().getName());
		ArrayList<String> l = new ArrayList<String>();
		
		Block targetblock = e.getClickedBlock();
		
		int X = targetblock.getLocation().getBlockX();
		int Y  = targetblock.getLocation().getBlockY();
		int Z  = targetblock.getLocation().getBlockZ();
		String s = X + "-" + Y + "-" + Z;

		if(fileConf.getList(s) != null){
			l = (ArrayList<String>) fileConf.getList(s);
			
			for(String s2 : l)
				CommandAusfuehren.Command(p, s2);
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		Block targetblock = (Block) e.getBlock();
		FileConfiguration fileConf = CommandDruckplatten.CDConf(e.getBlock().getWorld().getName());
		
		int X = targetblock.getLocation().getBlockX();
		int Y  = targetblock.getLocation().getBlockY();
		int Z  = targetblock.getLocation().getBlockZ();
		String s = X + "-" + Y + "-" + Z;
		
		if(fileConf.getList(s) != null) {
			fileConf.set(s, null);
			CommandDruckplatten.save(fileConf, e.getBlock().getWorld().getName());
		}
	}
}
