package essentials.commands.commandonobject;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import essentials.main.CommandAusfuehren;

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
			
			for(String s2 : l){
				String s3 = s2;
				s3 = s3.replaceAll("@p", p.getName());
				s3 = s3.replaceAll("@w", p.getWorld().getName());
				
				if(s3.contains("@a")) {
					String oldstring = s3;
					for(Player players : Bukkit.getOnlinePlayers()){
						s3 = oldstring;
						String s4 = s3.replace("@a", players.getName());
						CommandAusfuehren.commandstart(s4, p);
					}
				} else
					CommandAusfuehren.commandstart(s3, p);
			}	
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
