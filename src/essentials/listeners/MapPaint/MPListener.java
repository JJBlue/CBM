package essentials.listeners.MapPaint;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;

public class MPListener implements Listener {
	@EventHandler
	public void onMap(MapInitializeEvent e){
		MapView map = e.getMap();
		MPRenderer.setRenderer(map);
	}
	
	@EventHandler
	public void Interact(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		
		if(!MapPaint.containsPlayer(p) || !p.hasPermission("map.image")) return;
		
		String filename = MapPaint.getStringFromPlayer(p);
		MapPaint.removePainting(p);
		
		MPRenderer.paint(p, filename, event.getClickedBlock(), event.getBlockFace());
	}
}
