package essentials.listeners.MapPaint;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class MapPaint {
	private static HashMap<Player, String> paintings = new HashMap<Player, String>();
	
	public static void addPainting(Player p, String name){
		if(!p.hasPermission("map.image")) return;
		
		paintings.put(p, name);
		p.sendMessage("MapView add " + name);
	}
	
	public static void removePainting(Player p){
		if(containsPlayer(p))
			paintings.remove(p);
	}
	
	public static boolean containsPlayer(Player p){
		return paintings.containsKey(p);
	}
	
	public static String getStringFromPlayer(Player p){
		return paintings.get(p);
	}
}
