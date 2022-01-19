package cbm.modules.MapPaint;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import cbm.config.MainConfig;

public class MPRenderer {
	public static void paint(CommandSender commandSender, String filename, Block block, BlockFace blockFace) {
		File foto = new File(MainConfig.getDataFolder(), "picture/" + filename);

		Image image = null;
		try {
			image = ImageIO.read(foto);
		} catch (Exception e2) {
			commandSender.sendMessage("§4Bild konnte nicht geladen werden. Hier ihre Error Messages");
			return;
		}

		int height = image.getHeight(null);
		int width = image.getWidth(null);

		int ps = (int) Math.ceil(((double) height) / ((double) 128));
		int pw = (int) Math.ceil(((double) width) / ((double) 128));

		Location cbl = block.getLocation();
		World world = cbl.getWorld();
		int sx = cbl.getBlockX();
		int sy = cbl.getBlockY();
		int sz = cbl.getBlockZ();

		switch (blockFace) {
			case NORTH:
				sz--;
				break;
			case SOUTH:
				sz++;
				break;
			case EAST:
				sx++;
				break;
			case WEST:
				sx--;
				break;
			case UP:
				sy++;
				break;
			case DOWN:
				sy--;
				break;
			default:
				break;
		}

		for (int i = 0; i < ps; i++) {
			for (int y = 0; y < pw; y++) {
				int newX = sx;
				int newY = sy;
				int newZ = sz;

				switch (blockFace) {
					case NORTH:
						newX -= y;
						newY -= i;
						break;
					case EAST:
						newZ -= y;
						newY -= i;
						break;
					case SOUTH:
						newX += y;
						newY -= i;
						break;
					case WEST:
						newZ += y;
						newY -= i;
						break;
					case UP:
						newX += y;
						newZ += i;
						break;
					case DOWN:
						newX += y;
						newZ -= i;
						break;
					default:
						break;
				}

				Location l = new Location(world, newX, newY, newZ);
				if (!couldSpawnItemFrame(l)) continue;

				ItemFrame iFrame = null;
				try {
					iFrame = (ItemFrame) world.spawnEntity(l, EntityType.ITEM_FRAME);
				} catch (Exception ex) { //Exception Hanging ItemFrame in Air
					return; // or continue;
				}

				int maybeID = LoadMapPaint.get("", filename, 128 * y, 128 * i);
				ItemStack is = null;

				MapView mapView;

				if (maybeID > 0) {
					mapView = Bukkit.getMap(maybeID);
					setRenderer(mapView);
				} else {
					mapView = Bukkit.createMap(l.getWorld());

					LoadMapPaint.setMapPaint(mapView.getId(), "", filename, -128 * y, -128 * i);
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

		commandSender.sendMessage("§6Map Fertig geladen!");
	}

	public static boolean couldSpawnItemFrame(Location location) {
		if (!couldSpawnInIt(location)) return false;

		Location l2 = location.clone();

		if (l2.add(0, 1, 0).getBlock().getType().isSolid()) return true;
		l2.add(0, -1, 0);

		if (l2.add(0, -1, 0).getBlock().getType().isSolid()) return true;
		l2.add(0, 1, 0);

		if (l2.add(1, 0, 0).getBlock().getType().isSolid()) return true;
		l2.add(-1, 0, 0);

		if (l2.add(-1, 0, 0).getBlock().getType().isSolid()) return true;
		l2.add(1, 0, 0);

		if (l2.add(0, 0, 1).getBlock().getType().isSolid()) return true;
		l2.add(0, 0, -1);

		return l2.add(0, 0, -1).getBlock().getType().isSolid();
	}

	public static boolean couldSpawnInIt(Location location) {
		Block block = location.getBlock();
		if (block.isLiquid()) return true;

		Material material = block.getType();
		
		switch (material) {
			case AIR:
			case CAVE_AIR:
			case VOID_AIR:
				return true;
			default: return false;
		}
	}

	public static void setRenderer(MapView map) {
		if (!LoadMapPaint.contains(map.getId())) return;

		for (MapRenderer r : map.getRenderers()) map.removeRenderer(r);

		map.addRenderer(new MapRenderer() {
			Image img;
			boolean isRender = false;

			public void render(MapView view, MapCanvas canvas, Player p) {
				if (img == null) img = LoadMapPaint.getMapPaint(view.getId());
				if (img != null && !isRender) {
					canvas.drawImage(0, 0, img);
					isRender = true;
				}
			}
		});
	}
}
