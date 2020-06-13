package cbm.modules.holograms;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class HologramManager {
	private HologramManager() {}
	
	protected static Map<Location, Hologram> holograms = new HashMap<>();
	
	public static Hologram getHologram(Location location, double radius) {
		return getHologram(location, radius, null);
	}
	
	public static List<String> getIDs(Location location, double radius) {
		List<String> list = new LinkedList<>();
		
		Collection<Entity> entities = location.getWorld().getNearbyEntities(location, radius, radius, radius, (e) -> {
			return (e instanceof ArmorStand) && isHologramStartLine((ArmorStand) e);
		});
		
		entities.forEach((e) -> {
			HologramLine line = new HologramLine((ArmorStand) e);
			list.add(line.getID());
		});
		
		return list;
	}
	
	public static Hologram getHologram(Location location, double radius, String ID) {
		Collection<Entity> entities = location.getWorld().getNearbyEntities(location, radius, radius, radius, (e) -> {
			return (e instanceof ArmorStand) && isHologramStartLine((ArmorStand) e);
		});
		
		if(entities.isEmpty()) return null;
		
		if(ID != null)
			entities.removeIf((e) -> !new HologramLine((ArmorStand) e).getID().equalsIgnoreCase(ID));
		
		Entity entity = entities.iterator().next();
		
		HologramLine line = new HologramLine((ArmorStand) entity);
		Hologram hologram = new Hologram(entity.getLocation(), line.getID());
		hologram.setHologramLine(line);
		
		fillHologram(hologram, hologram.getID());
		
		return hologram;
	}
	
	public static boolean isHologramStartLine(ArmorStand armorStand) {
		return HologramLine.isHologramStartLine(armorStand);
	}
	
	public static boolean isHologramLine(ArmorStand armorStand) {
		return HologramLine.isHologramLine(armorStand);
	}
	
	protected static void fillHologram(Hologram hologram, String ID) {
		Location location = hologram.getLocation().clone();
		int radius = 1;
		World world = location.getWorld();
		
		int lastSize;
		
		do {
			lastSize = hologram.getLines().size();
			
			world.getNearbyEntities(location, radius, radius, radius, (e) -> {
				if(e instanceof ArmorStand) {
					HologramLine line = new HologramLine((ArmorStand) e);
					if(!line.getID().equalsIgnoreCase(ID)) return false;
					hologram.setHologramLine(line);
					return true;
				}
				
				return false;
			});
			
			location = location.add(0, - (radius + 0.5), 0);
		} while(hologram.getLines().size() != lastSize);
	}
}
