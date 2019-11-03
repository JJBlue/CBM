package essentials.modules.claim;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

public class ClaimRegion {
	
	public synchronized static boolean claim(World world, int minX, int minZ, int maxX, int maxZ) {
		return claim(null, world, minX, minZ, maxX, maxZ);
	}

	public synchronized static boolean claimChunk(Player player, Chunk chunk) {
		return claim(player, chunk.getWorld(), chunk.getX() * 16, chunk.getZ() * 16, chunk.getX() * 16 + 15, chunk.getZ() * 16 + 15);
	}
	
	public synchronized static boolean claim(Player player, World world, int minX, int minZ, int maxX, int maxZ) {
		RegionManager manager = getRegionManager(world);
		if (manager == null) return false;
		
		String id;
		int number = 1;
		
		do {
			if(player != null)
				id = "plot:" + player.getName() + ":" + number;
			else
				id = "plot:" + number;
		} while(manager.hasRegion(id));
		
		ProtectedCuboidRegion region = new ProtectedCuboidRegion(
			id,
			BukkitAdapter.asBlockVector(new Location(world, minX, 0, minZ)),
			BukkitAdapter.asBlockVector(new Location(world, maxX, 255, maxZ))
		);

		if(player != null) {
			DefaultDomain owners = region.getOwners();
			owners.addPlayer(player.getUniqueId());
		}
		
		manager.addRegion(region);
		return true;
	}
	
	public synchronized static boolean unclaimChunk(Chunk chunk) {
		return unclaim(chunk.getWorld(), chunk.getX() * 16, chunk.getZ() * 16, chunk.getX() * 16 + 15, chunk.getZ() * 16 + 15);
	}
	
	public synchronized static boolean unclaim(World world, int minX, int minZ, int maxX, int maxZ) {
		RegionManager manager = getRegionManager(world);
		if (manager == null) return false;
		
		//TODO
		
		return true;
	}
	
	public synchronized static boolean contains(World world, int minX, int minZ, int maxX, int maxZ) {
		RegionManager manager = getRegionManager(world);
		if (manager == null) return true;
		
		ProtectedCuboidRegion region = new ProtectedCuboidRegion(
			"test",
			BukkitAdapter.asBlockVector(new Location(world, minX, 0, minZ)),
			BukkitAdapter.asBlockVector(new Location(world, maxX, 255, maxZ))
		);
		
		manager.overlapsUnownedRegion(region, null);
		
//		manager.getApplicableRegionsIDs(BlockVector3.at(x, 100, z));
		//TODO
		
		return false;
	}
	
	public synchronized static boolean list(Player player, World world) {
		RegionManager manager = getRegionManager(world);
		if (manager == null) return true;
		
//		manager.getApplicableRegionsIDs(BlockVector3.at(x, 100, z));
		manager.getRegions();
		//TODO
		
		return false;
	}
	
	public static RegionManager getRegionManager(World world) {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		if(container == null) return null;
		RegionManager manager = container.get(BukkitAdapter.adapt(world));
		return manager;
	}
}
