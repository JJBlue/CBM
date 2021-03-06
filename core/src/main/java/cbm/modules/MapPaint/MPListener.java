package cbm.modules.MapPaint;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;

import cbm.player.PlayerConfig;
import cbm.player.PlayerManager;

public class MPListener implements Listener {
	@EventHandler
	public void onMap(MapInitializeEvent e) {
		MapView map = e.getMap();
		MPRenderer.setRenderer(map);
	}

	@EventHandler
	public void Interact(PlayerInteractEvent event) {
		Player p = event.getPlayer();

		PlayerConfig config = PlayerManager.getConfig(p);
		if (!config.containsLoadedKey("mapPaintImage")) return;

		String filename = config.getString("mapPaintImage");
		config.removeBuffer("mapPaintImage");

		MPRenderer.paint(p, filename, event.getClickedBlock(), event.getBlockFace());
	}
}
