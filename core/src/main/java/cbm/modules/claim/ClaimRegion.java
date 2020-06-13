package cbm.modules.claim;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

public class ClaimRegion {
	public synchronized static boolean claimChunk(Player player, Chunk chunk) {
		return claim(player, chunk.getWorld(), chunk.getX() * 16, 0, chunk.getZ() * 16, chunk.getX() * 16 + 15, 255, chunk.getZ() * 16 + 15);
	}
	
	public synchronized static boolean claim(Player player, World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		RegionManager manager = getRegionManager(world);
		if (manager == null) return false;
		
		String id;
		int number = 1;
		
		do {
			if(player != null)
				id = "plot-" + player.getName() + "-" + number;
			else
				id = "plot-" + number;
			
			number++;
		} while(manager.hasRegion(id));
		
		ProtectedCuboidRegion region = new ProtectedCuboidRegion(
			id,
			BukkitAdapter.asBlockVector(new Location(world, minX, minY, minZ)),
			BukkitAdapter.asBlockVector(new Location(world, maxX, maxY, maxZ))
		);

		if(player != null) {
			DefaultDomain owners = region.getOwners();
			owners.addPlayer(player.getUniqueId());
		}
		
		manager.addRegion(region);
		return true;
	}
	
	public synchronized static boolean unclaimChunk(Player player, Chunk chunk) {
		return unclaim(player, chunk.getWorld(), chunk.getX() * 16, 0, chunk.getZ() * 16);
	}
	
	public synchronized static boolean unclaim(Player player, World world, int x, int y, int z) {
		RegionManager manager = getRegionManager(world);
		if (manager == null) return false;
		
		
		List<String> ids = manager.getApplicableRegionsIDs(BlockVector3.at(x, y, z));
		boolean deletedRegion = false;
		
		for(String id : ids) {
			if(id.contains("plot")) {
				ProtectedRegion region = manager.getRegion(id);
				DefaultDomain owners = region.getOwners();
				if(owners.contains(player.getUniqueId())) {
					deletedRegion = true;
					manager.removeRegion(id);
					Bukkit.broadcastMessage("Deleted Region " + id); //TODO
					break;
				}
			}
		}
		
		return deletedRegion;
	}
	
	public synchronized static boolean contains(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		RegionManager manager = getRegionManager(world);
		if (manager == null) return true;
		
		ProtectedCuboidRegion selection = new ProtectedCuboidRegion(
			"selection",
			BukkitAdapter.asBlockVector(new Location(world, minX, minY, minZ)),
			BukkitAdapter.asBlockVector(new Location(world, maxX, maxY, maxZ))
		);
		
		ApplicableRegionSet regionSet = manager.getApplicableRegions(selection);
		if(regionSet.isVirtual()) {
			Bukkit.broadcastMessage("is virtual!"); //TODO
			return true;
		}
		
		Iterator<ProtectedRegion> iterator = regionSet.iterator();
		while(iterator.hasNext()) {
			iterator.next();
			Bukkit.broadcastMessage("contains region"); //TODO
			return true;
		}
		
		return false;
	}
	
	public synchronized static boolean list(Player player, World world) {
		RegionManager manager = getRegionManager(world);
		if (manager == null) return true;
		
//		manager.getApplicableRegionsIDs(BlockVector3.at(x, 100, z));
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
