package essentials.modules.holograms;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class HologramManager {
	private HologramManager() {}
	
	protected static Map<Location, Hologram> holograms = new HashMap<>();
	
	public static Hologram getHologram(Location location, int radius) {
		Collection<Entity> entities = location.getWorld().getNearbyEntities(location, radius, radius, radius, (e) -> {
			return e instanceof ArmorStand && isHologramStartLine((ArmorStand) e);
		});
		
		if(entities.isEmpty()) return null;
		if(entities.size() == 1) {
			Entity entity = entities.iterator().next();
			return new Hologram(entity.getLocation()); //TODO
		}
		
		return null; //TODO
	}
	
	public static boolean isHologramStartLine(ArmorStand armorStand) {
		return HologramLine.isHologramStartLine(armorStand);
	}
	
	public static boolean isHologramLine(ArmorStand armorStand) {
		return HologramLine.isHologramLine(armorStand);
	}
	
	protected static List<HologramLine> getHologramLines(Location location) {
		return null; //TODO
	}
}
