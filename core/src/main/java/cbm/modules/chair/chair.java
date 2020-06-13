package cbm.modules.chair;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import cbm.player.PlayerConfig;
import cbm.player.PlayerManager;

public class chair implements Listener {
	public static boolean toggle(Player player) {
		PlayerConfig config = PlayerManager.getConfig(player);

		if (!config.containsLoadedKey("chair")) {
			config.setTmp("chair", true);
			return true;
		}

		config.removeBuffer("chair");
		return false;
	}

	public static void sit(Player p, Location l) {
		if (l == null || p == null) return;
		
		l.getBlock();

		String clickedType = l.getBlock().getType().name().toLowerCase();

		//mitte
		if (clickedType.contains("slab") || clickedType.contains("head") || clickedType.contains("stair") || clickedType.contains("pot") || clickedType.contains("bed"))
			l = new Location(l.getWorld(), l.getBlockX() + 0.50, l.getBlockY() - 0.4, l.getBlockZ() + 0.500);
		else if (clickedType.contains("carpet")) //unten
			l = new Location(l.getWorld(), l.getBlockX() + 0.50, l.getBlockY() - 0.95, l.getBlockZ() + 0.500);
		else
			l = new Location(l.getWorld(), l.getBlockX() + 0.50, l.getBlockY(), l.getBlockZ() + 0.500);


		ArmorStand e = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
		e.setVisible(false);
		e.setGravity(false);
		e.setSmall(true);
		e.setCanPickupItems(false);
		e.setMarker(false);
		e.setRemoveWhenFarAway(true);
		e.setCustomName("§4Chair");
		e.addPassenger(p);
	}

	public static void sitEntity(Player p, Entity e) {
		e.addPassenger(p);
	}

	@EventHandler
	private void InteractEvent(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		if (e.getClickedBlock() == null) return;
		if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) return;

		PlayerConfig config = PlayerManager.getConfig(player);
		if (!config.containsLoadedKey("chair") || !config.getBoolean("chair")) return;

		Block b = e.getClickedBlock();
		Location l = b.getLocation();

		sit(player, l);
	}

	@EventHandler
	private void Interact(PlayerInteractAtEntityEvent e) {
		Player player = e.getPlayer();

		PlayerConfig config = PlayerManager.getConfig(player);
		if (!config.containsLoadedKey("chair") || !config.getBoolean("chair")) return;
		sitEntity(player, e.getRightClicked());
	}

	@EventHandler
	private void exit(EntityDismountEvent event) {
		Entity e = event.getDismounted();

		if (e.getCustomName() == null) return;
		if (!e.getCustomName().equalsIgnoreCase("§4Chair")) return;

		if (e.getPassengers() == null || e.getPassengers().isEmpty() || e.getPassengers().size() == 1) //Da der Spieler noch als Passenger zaehlt
			e.remove();
	}
}
