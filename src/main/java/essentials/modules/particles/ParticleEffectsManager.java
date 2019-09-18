package essentials.modules.particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class ParticleEffectsManager {
	/**
	 * 
	 * @param center
	 * @param distance if center is not null && spawnParticle Location < distance to center it will be spawn
	 * @param particle
	 * @param from
	 * @param to
	 * @param count
	 * @param color of Particle
	 * @param size of Particle
	 */
	public static void spawnWall(Location center, double distance, Particle particle, Location from, Location to, int count, Color color, float size) {
		if(from.getWorld() != to.getWorld()) return;
		
		World world = from.getWorld();
		
		for(int x = Math.min(from.getBlockX(), to.getBlockX()); x <= Math.max(from.getBlockX(), to.getBlockX()); x++) {
			if(center != null && abs(center.getBlockX() - x) > distance)
				continue;
			
			for(int y = Math.min(from.getBlockY(), to.getBlockY()); y <= Math.max(from.getBlockY(), to.getBlockY()); y++) {
				if(center != null && abs(center.getBlockY() - y) > distance)
					continue;
				
				for(int z = Math.min(from.getBlockZ(), to.getBlockZ()); z <= Math.max(from.getBlockZ(), to.getBlockZ()); z++) {
					if(center != null && abs(center.getBlockZ() - z) > distance)
						continue;
					
					world.spawnParticle(particle, x, y, z, count, 0, 0, 0, 0, new Particle.DustOptions(color, size), false);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param center
	 * @param distance
	 * @param particle
	 * @param loc
	 * @param height max Height of the Particles
	 * @param addHeightPerParticle default 0.05
	 * @param addRadiusPerParticle default 1
	 * @param radius
	 * @param count
	 * @param color
	 * @param size
	 */
	public static void spawnSpiral(Location center, double distance, Particle particle, Location loc, int height, double addHeightPerParticle, double radius, double addRadiusPerParticle, int count, Color color, float size) {
		double curRadius = 0;
		World world = loc.getWorld();
		
		for(double y = loc.getY(); abs(loc.getY() - y) <= height; y += addHeightPerParticle) {
			if(center != null && abs(center.getY() - y) > distance)
				continue;
			
			curRadius += addRadiusPerParticle;
			
			double dX = Math.sin(curRadius) * radius;
			double dZ = Math.cos(curRadius) * radius;
			
			world.spawnParticle(particle, loc.getX() + dX, y, loc.getZ() + dZ, count, 0, 0, 0, 0, new Particle.DustOptions(color, size), false);
		}
	}
	
	private static int abs(int value) {
		if(value < 0) return value * (-1);
		return value;
	}
	
	private static double abs(double value) {
		if(value < 0) return value * (-1);
		return value;
	}
}
