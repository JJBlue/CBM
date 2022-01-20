package cbm.modules.claim;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import cbm.main.Main;
import cbm.modules.particles.ParticleEffectsManager;

public class ClaimShowTimer {
	private static final Set<Player> players = Collections.synchronizedSet(new HashSet<>());
	private static int taskID = -1;
	
	public static void addPlayer(Player player) {
		players.add(player);
		start();
	}
	
	public static void removePlayer(Player player) {
		players.remove(player);
	}
	
	public synchronized static void start() {
		if(taskID > 0) return;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
			if(players.isEmpty()) stop();
			
			List<World> worlds = new LinkedList<>();
			
			synchronized (players) {
				for(Player player : players)
					worlds.add(player.getWorld());
			}
			
			RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
			
			for(World world : worlds) {
				RegionManager manager = container.get(BukkitAdapter.adapt(world));
				if (manager == null) continue;
				
				Map<String, ProtectedRegion> regions = manager.getRegions();
				synchronized (players) {
					regions.forEach((key, region) -> {
						for(Player player : players) {
							if(player.isOp() || player.hasPermission("claim.show.all") || region.getOwners().contains(player.getUniqueId()) || region.getMembers().contains(player.getUniqueId())){
								
								BlockVector2[] arrays = new BlockVector2[region.getPoints().size()];
								region.getPoints().toArray(arrays);
								
								int minY = region.getMinimumPoint().getBlockY();
								int maxY = region.getMaximumPoint().getBlockY();
								
								for(int i = 0; i < arrays.length; i++) {
									BlockVector2 v1 = arrays[i];
									BlockVector2 v2 = (i == arrays.length - 1 ? arrays[0] : arrays[i + 1]);
									
									ParticleEffectsManager.spawnWall(
										player,
										50d,
										Particle.REDSTONE,
										new Location(player.getWorld(), v1.getBlockX(), minY, v1.getBlockZ()),
										new Location(player.getWorld(), v2.getBlockX(), maxY, v2.getBlockZ()),
										1,
										Color.RED,
										20f
									);
								}
								
							}
						}
					});
				}
				
			}
		}, 0L, 40L);
	}
	
	public synchronized static void stop() {
		if(taskID < 0) return;
		
		Bukkit.getScheduler().cancelTask(taskID);
		taskID = -1;
	}
}
