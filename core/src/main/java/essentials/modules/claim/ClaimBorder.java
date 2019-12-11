package essentials.modules.claim;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class ClaimBorder {
	public static void createFence(World world, int minX, int minZ, int maxX, int maxZ) {
		List<Material> fences = new LinkedList<>();
		fences.add(Material.ACACIA_FENCE);
		fences.add(Material.BIRCH_FENCE);
		fences.add(Material.DARK_OAK_FENCE);
		fences.add(Material.OAK_FENCE);
		fences.add(Material.JUNGLE_FENCE);
		fences.add(Material.OAK_FENCE);
		fences.add(Material.SPRUCE_FENCE);
		
		Material fence = fences.get(new Random().nextInt(fences.size()));
		
		place(fence, world, minX, minZ, maxX, maxZ);
	}
	
	public static void place(Material material, World world, int minX, int minZ, int maxX, int maxZ) {
		for(int i = minX; i <= maxX; i++) {
			place(material, world, i, minZ);
			place(material, world, i, maxZ);
		}
		
		for(int i = minZ + 1; i < maxZ; i++) {
			place(material, world, minX, i);
			place(material, world, maxX, i);
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
