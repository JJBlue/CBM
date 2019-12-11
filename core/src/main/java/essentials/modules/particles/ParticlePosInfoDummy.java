package essentials.modules.particles;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class ParticlePosInfoDummy {
	public final Object pos;
	public final double distance;
	
	public ParticlePosInfoDummy(World world) {
		pos = world;
		distance = 0;
	}
	
	public ParticlePosInfoDummy(Player player, double distance) {
		pos = player;
		this.distance = distance;
	}
	
	public boolean isPlayer() {
		return pos instanceof Player;
	}
	
	public Player getPlayer() {
		if(pos instanceof Player)
			return (Player) pos;
		return null;
	}
	
	public boolean isWorld() {
		return pos instanceof World;
	}
	
	public World getWorld() {
		if(pos instanceof World)
			return (World) pos;
		return null;
	}
}
