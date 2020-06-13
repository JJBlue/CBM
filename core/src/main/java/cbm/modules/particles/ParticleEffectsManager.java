package cbm.modules.particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ParticleEffectsManager {
	
	public static void spawnWall(World world, Particle particle, Location from, Location to, int count, Color color, float size) {
		spawnWall(new ParticlePosInfoDummy(world), particle, from, to, count, color, size);
	}
	
	public static void spawnWall(Player player, double distance, Particle particle, Location from, Location to, int count, Color color, float size) {
		spawnWall(new ParticlePosInfoDummy(player, distance), particle, from, to, count, color, size);
	}
	
	public static void spawnWall(ParticlePosInfoDummy dummy, Particle particle, Location from, Location to, int count, Color color, float size) {
		if(from.getWorld() != to.getWorld()) return;
		Location center = (dummy.isPlayer() ? dummy.getPlayer().getLocation() : null);
		
		for(int x = Math.min(from.getBlockX(), to.getBlockX()); x <= Math.max(from.getBlockX(), to.getBlockX()); x++) {
			if(center != null && abs(center.getBlockX() - x) > dummy.distance)
				continue;
			
			for(int y = Math.min(from.getBlockY(), to.getBlockY()); y <= Math.max(from.getBlockY(), to.getBlockY()); y++) {
				if(center != null && abs(center.getBlockY() - y) > dummy.distance)
					continue;
				
				for(int z = Math.min(from.getBlockZ(), to.getBlockZ()); z <= Math.max(from.getBlockZ(), to.getBlockZ()); z++) {
					if(center != null && abs(center.getBlockZ() - z) > dummy.distance)
						continue;
					
					spawnParticle(dummy, particle, x + 0.5, y + 0.5, z + 0.5, count, 0, 0, 0, 0, new Particle.DustOptions(color, size), false);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param dummy
	 * @param particle
	 * @param loc
	 * @param height max Height of the Particles
	 * @param addHeightPerParticle default 0.05
	 * @param addRadiusPerParticle default 1
	 * @param radius: Player distance from Particle
	 * @param count
	 * @param color
	 * @param size
	 */
	public static void spawnSpiral(ParticlePosInfoDummy dummy, Particle particle, Location loc, int height, double addHeightPerParticle, double radius, double addRadiusPerParticle, int count, Color color, float size) {
		Location center = (dummy.isPlayer() ? dummy.getPlayer().getLocation() : null);
		
		if(center != null && radius > dummy.distance)
			return;
		
		double curRadius = 0;
		
		for(double y = loc.getY(); abs(loc.getY() - y) <= height; y += addHeightPerParticle) {
			if(center != null && abs(center.getY() - y) > dummy.distance)
				continue;
			
			curRadius += addRadiusPerParticle;
			
			spawnSpiralHelper(dummy, particle, loc, y, radius, curRadius, count, color, size);
		}
	}
	
	public static void spawnSpiralHelper(ParticlePosInfoDummy dummy, Particle particle, Location loc, double currentHeight, double radius, double currentRadius, int count, Color color, float size) {
		Location center = (dummy.isPlayer() ? dummy.getPlayer().getLocation() : null);
		
		if(center != null && abs(center.getY() - currentHeight) > dummy.distance)
			return;
		
		double dX = Math.sin(currentRadius) * radius;
		double dZ = Math.cos(currentRadius) * radius;
		
		spawnParticle(dummy, particle, loc.getX() + dX, currentHeight, loc.getZ() + dZ, count, 0, 0, 0, 0, new Particle.DustOptions(color, size), false);
	}
	
	public static void spawnCircle(ParticlePosInfoDummy dummy, Particle particle, Location loc, double radius, int countCircle, int count, Color color, float size) {
		Location center = (dummy.isPlayer() ? dummy.getPlayer().getLocation() : null);
		
		if(center != null && radius > dummy.distance)
			return;
		
		for(double i = 0; i < 360; i += (360d/countCircle)) {
			double dX = Math.sin(i) * radius;
			double dZ = Math.cos(i) * radius;
			
			spawnParticle(dummy, particle, loc.getX() + dX, loc.getY(), loc.getZ() + dZ, count, 0, 0, 0, 0, new Particle.DustOptions(color, size), false);
		}
	}
	
	public static <T extends Particle.DustOptions> void spawnParticle(ParticlePosInfoDummy dummy, Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, T data, boolean force) {
		if(dummy.isPlayer())
			dummy.getPlayer().spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, data);
		else if(dummy.isWorld())
			dummy.getWorld().spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, data, force);
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
