package essentials.listeners.FlyThrowBlocks;

import java.util.LinkedList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FTB implements Listener{
	public static LinkedList<Player> players = new LinkedList<>();
	
	public static boolean toogle(Player player) {
		if(!FTB.players.contains(player)) {
			FTB.players.add(player);
			return true;
		}
		
		FTB.players.remove(player);
		return false;
	}
	
	@EventHandler
	public void Move(PlayerMoveEvent e){
		Player p = e.getPlayer();
		
		if(players.contains(p) && (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))){
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
	}
	
	private boolean cantWalk(Location l){
		if(l != null){
			switch(l.getBlock().getType()){
				case ARMOR_STAND:
				case BRAIN_CORAL:
				case BROWN_MUSHROOM:
				case BUBBLE_CORAL:
				case CARROT:
				case CARROTS:
				case CHARCOAL:
				case COMPARATOR:
				case DEAD_BRAIN_CORAL:
				case DEAD_BUBBLE_CORAL:
				case DEAD_BUSH:
				case DEAD_FIRE_CORAL:
				case DEAD_HORN_CORAL:
				case DEAD_TUBE_CORAL:
				case DETECTOR_RAIL:
				case FERN:
				case GRASS:
				case HORN_CORAL:
				case KELP:
				case KELP_PLANT:
				case LADDER:
				case LAVA:
				case LEVER:
				case LILY_PAD:
				case ORANGE_TULIP:
				case OXEYE_DAISY:
				case PAINTING:
				case PEONY:
				case PINK_TULIP:
				case POTATO:
				case POTATOES:
				case RED_MUSHROOM:
				case RED_TULIP:
				case REPEATER:
				case ROSE_BUSH:
				case SEAGRASS:
				case SEA_PICKLE:
				case SHEARS:
				case STRING:
				case SUNFLOWER:
				case TALL_GRASS:
				case TALL_SEAGRASS:
				case TUBE_CORAL:
				case VINE:
				case WHITE_TULIP:
					return false;
				default:
					break;
			}
			
			String material = l.getBlock().getType().toString().toLowerCase();
			
			if(material.contains("air")) return false;
			else if(material.contains("banner")) return false;
			else if(material.contains("carpet")) return false;
			else if(material.contains("plate")) return false;
			else if(material.contains("torch")) return false;
			else if(material.contains("button")) return false;
			else if(material.contains("void")) return false;
			else if(material.contains("sugar")) return false;
			else if(material.contains("fan")) return false;
			else if(material.contains("water")) return false;
			else if(material.contains("sapling")) return false;
			else if(material.contains("tripwire")) return false;
			else if(material.contains("potted")) return false;
			else if(material.contains("rail")) return false;
			else if(material.contains("redstone")) return false;
			else if(material.contains("wheat")) return false;
			else if(material.contains("beetroot")) return false;
			else if(material.contains("seed")) return false;
			else if(material.contains("item")) return false;
			else if(material.contains("fire")) return false;
		}
		
		Material material = l.getBlock().getType();
		if(material.toString().contains("SIGN"))
			return false;
		
		return true;
	}
}
