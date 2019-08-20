package essentials.listeners.MapPaint;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class PlayerMapListener implements Listener{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void Interact(PlayerInteractEvent e){
		Player p = e.getPlayer();
		
		if(!MapPaint.containsPlayer(p) || !p.hasPermission("map.image")) return;
		
		p.sendMessage("Try painting");
		e.getClickedBlock().setType(Material.AIR);
		
		String filename = MapPaint.getStringFromPlayer(p);
		File foto = new File("plugins/Allgemein/picture", filename);
		MapPaint.removePainting(p);

		Image image = null;
		try {
			image = ImageIO.read(foto);
		} catch(Exception e2) {
			p.sendMessage("§4Bild konnte nicht geladen werden. Hier ihre Error Messages");
		}
		
		if(image == null) return;
		
		int height = image.getHeight(null);
		int width = image.getWidth(null);
		
		int ps = (int) Math.ceil(((double) height) / ((double) 128));
		int pw = (int) Math.ceil(((double) width) / ((double) 128));
		
		int sx = e.getClickedBlock().getLocation().getBlockX();
		int sy = e.getClickedBlock().getLocation().getBlockY();
		int sz = e.getClickedBlock().getLocation().getBlockZ();
		
		for(int i = 0; i < ps; i++){						
			for(int y = 0; y < pw; y++){
				int newX;
				int newY = sy - i;
				int newZ;
				
				if(e.getBlockFace() == BlockFace.NORTH){
					newX = sx - y;
					newZ = sz;
				}else if(e.getBlockFace() == BlockFace.EAST){
					newZ = sz - y;
					newX = sx;
				}else if(e.getBlockFace() == BlockFace.SOUTH){
					newX = sx + y;
					newZ = sz;
				}else if(e.getBlockFace() == BlockFace.WEST){
					newZ = sz + y;
					newX = sx;
				} else
					break;
				
				Location l = new Location(e.getClickedBlock().getWorld(), newX, newY, newZ);
				ItemFrame iFrame = null;
				try {
					iFrame = (ItemFrame) l.getWorld().spawnEntity(l, EntityType.ITEM_FRAME);
				}catch(Exception yy){}
				
				if(iFrame == null) return;
				
				int maybeID = LoadMapPaint.get("plugins/Allgemein/picture", filename, 128*y, 128*i);
				ItemStack is = null;
				
				MapView mapView;
				
				if(maybeID > 0) {
					mapView = Bukkit.getMap(maybeID);
//					p.sendMessage("Found Image with ID: " + maybeID);
					setRenderer(mapView);
				} else {
					mapView = Bukkit.createMap(l.getWorld());
					
//					p.sendMessage("Bild mit ID: " + mapView.getId() + " wurde erstellt");
					LoadMapPaint.setMapPaint(mapView.getId(), "plugins/Allgemein/picture", filename, -128*y, -128*i);
					setRenderer(mapView);
				}
				
				is = new ItemStack(Material.FILLED_MAP);
				MapMeta mapMeta = (MapMeta) is.getItemMeta();
				mapMeta.setScaling(true);
				mapMeta.setMapView(mapView);
				is.setItemMeta(mapMeta);
				
				iFrame.setItem(is);
			}
		}
		
		p.sendMessage("§6Map Fertig geladen!");
	}
	
	@EventHandler
	public void onMap(MapInitializeEvent e){
		MapView map = e.getMap();
		setRenderer(map);
	}
	
	public void setRenderer(MapView map) {
		if(!LoadMapPaint.contains(map.getId())) return;
		
		for(MapRenderer r : map.getRenderers()) map.removeRenderer(r);
		
		map.addRenderer(new MapRenderer() {
			Image img;
			boolean isRender = false;
			
			public void render(MapView view, MapCanvas canvas, Player p) {
				if(img == null) img = LoadMapPaint.getMapPaint(view.getId());
				if(img != null && !isRender) {
					canvas.drawImage(0, 0, img);
					isRender = true;
				}
			}
		});
	}
}
