package essentials.modules.claim;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

public class ClaimRegion {
	
	public synchronized static void claim(Player player, World world, int minX, int minZ, int maxX, int maxZ) {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager manager = container.get(BukkitAdapter.adapt(world));
		
		String id;
		int number = 1;
		
		do {
			id = "plot_" + player.getName() + "_" + number;
		} while(manager.hasRegion(id));
		
		ProtectedCuboidRegion region = new ProtectedCuboidRegion(
			id,
			BukkitAdapter.asBlockVector(new Location(world, minX, 0, minZ)),
			BukkitAdapter.asBlockVector(new Location(world, maxX, 255, maxZ))
		);
		
		DefaultDomain owners = region.getOwners();
		owners.addPlayer(player.getUniqueId());
		
		manager.addRegion(region);
		
		createFence(world, minX, minZ, maxX, maxZ);
	}
	
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
