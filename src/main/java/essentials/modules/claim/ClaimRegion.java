package essentials.modules.claim;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ClaimRegion {
	
	public static void claim(Player player, World world, int minX, int minZ, int maxX, int maxZ) {
		
	}
	
	public static void createFence(World world, int minX, int minZ, int maxX, int maxZ) {
		Material fence;
		
		{
			List<Material> fences = new LinkedList<>();
			fences.add(Material.ACACIA_FENCE);
			fences.add(Material.BIRCH_FENCE);
			fences.add(Material.DARK_OAK_FENCE);
			fences.add(Material.OAK_FENCE);
			fences.add(Material.JUNGLE_FENCE);
			fences.add(Material.OAK_FENCE);
			fences.add(Material.SPRUCE_FENCE);
			
			fence = fences.get(new Random().nextInt(fences.size()));
		}
		
		
		for(int i = minX; i <= maxX; i++) {
			place(fence, world, i, minZ);
			place(fence, world, i, maxZ);
		}
		
		for(int i = minZ + 1; i < maxZ; i++) {
			place(fence, world, minX, i);
			place(fence, world, maxX, i);
		}
	}
	
	public static void place(Material material, World world, int x, int z) {
		int y = 255;
		Location location = new Location(world, x, y, z);
		
		Block nextBlock;
		
		while(y >= 0) {
			nextBlock = world.getBlockAt(x, y--, z);
			String type = nextBlock.getType().name().toLowerCase();
			
			if(type.contains("glass")) continue;
			if(type.contains("leave")) continue;
			if(type.contains("log")) continue;
			if(type.contains("air")) {
				location = new Location(world, x, y + 1, z);
				continue;
			}
			
			break;
		}
		
		location.getBlock().setType(material);
	}
}
