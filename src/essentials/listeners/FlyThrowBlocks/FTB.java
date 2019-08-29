package essentials.listeners.FlyThrowBlocks;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;

public class FTB implements Listener{
	public static boolean toogle(Player player) {
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(player);
		
		boolean newValue = !playerConfig.getBoolean(PlayerConfigKey.tWallGhost);
		playerConfig.set(PlayerConfigKey.tWallGhost, newValue);
		
		return newValue;
	}
	
	@EventHandler
	public void Move(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(!p.getGameMode().equals(GameMode.CREATIVE) && !p.getGameMode().equals(GameMode.SPECTATOR)) return;
		
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);
		if(!playerConfig.getBoolean(PlayerConfigKey.tWallGhost)) return;
		
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		World w = p.getWorld();
		
		
		if(		cantWalk(new Location(w, x + 0.8, y, z)) && cantWalk(new Location(w, x + 0.8, y + 1, z)) ||
				cantWalk(new Location(w, x - 0.8, y, z)) && cantWalk(new Location(w, x - 0.8, y + 1, z)) ||
				cantWalk(new Location(w, x, y, z + 0.8)) && cantWalk(new Location(w, x, y + 1, z + 0.8)) ||
				cantWalk(new Location(w, x, y, z - 0.8)) && cantWalk(new Location(w, x, y + 1, z - 0.8)) ||
				cantWalk(new Location(w, x, y + 1.9, z)) ||
				cantWalk(new Location(w, x, y - 0.8, z)) && p.isSneaking() ||
				cantWalk(p.getLocation()) && cantWalk(new Location(w, x, y + 1, z))){
				
			if(p.getGameMode().equals(GameMode.CREATIVE))
				p.setGameMode(GameMode.SPECTATOR);
		} else if(p.getGameMode().equals(GameMode.SPECTATOR))
			p.setGameMode(GameMode.CREATIVE);
	}
	
	private boolean cantWalk(Location l){
		Block block = l.getBlock();
		
		return !(block.isLiquid() || block.isPassable() || block.isEmpty());
		
//		String material = l.getBlock().getType().toString().toLowerCase();
//		
//		if(material.contains("air")) return false;
//		else if(material.contains("banner")) return false;
//		else if(material.contains("carpet")) return false;
//		else if(material.contains("plate")) return false;
//		else if(material.contains("torch")) return false;
//		else if(material.contains("button")) return false;
//		else if(material.contains("void")) return false;
//		else if(material.contains("sugar")) return false;
//		else if(material.contains("fan")) return false;
//		else if(material.contains("water")) return false;
//		else if(material.contains("sapling")) return false;
//		else if(material.contains("tripwire")) return false;
//		else if(material.contains("potted")) return false;
//		else if(material.contains("rail")) return false;
//		else if(material.contains("redstone")) return false;
//		else if(material.contains("wheat")) return false;
//		else if(material.contains("beetroot")) return false;
//		else if(material.contains("seed")) return false;
//		else if(material.contains("item")) return false;
//		else if(material.contains("fire")) return false;
//		
//		Material material = l.getBlock().getType();
//		if(material.toString().contains("SIGN"))
//			return false;
//		
//		return true;
	}
}
