package cbm.modules.troll.control;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import cbm.main.Main;

public class ControlListener implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void chat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if(ControlManager.isControlSomeone(player)) {
			event.setCancelled(true);
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
				ControlManager.getControlledPlayer(player).chat(event.getMessage());
			}, 0L);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void pickup(EntityPickupItemEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		
		if(ControlManager.isControlSomeone(player)) {
			Player controlled = ControlManager.getControlledPlayer(player);
			controlled.getInventory().setContents(player.getInventory().getContents());
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void pickup(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if(ControlManager.isControlSomeone(player)) {
			Player controlled = ControlManager.getControlledPlayer(player);
			controlled.getInventory().setContents(player.getInventory().getContents());
			controlled.getInventory().setItem(event.getSlot(), event.getCursor());
		} else if(ControlManager.isControlled(player))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void inventoryDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		
		if(ControlManager.isControlSomeone(player)) {
			Player controlled = ControlManager.getControlledPlayer(player);
			controlled.getInventory().setContents(player.getInventory().getContents());
		} else if (ControlManager.isControlled(player)) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		
		if (ControlManager.isControlled(player)) {
			e.setCancelled(true);
			return;
		}

		if (ControlManager.isControlSomeone(player)) {
			Location to = e.getTo();
			if (to == null) return;
			ControlManager.getControlledPlayer(player).teleport(e.getTo());
		}
	}
	
	@EventHandler
	public void changeSlot(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		
		if(ControlManager.isControlled(player)) {
			Player controller = ControlManager.getControllerPlayer(player);
			if(event.getNewSlot() != controller.getInventory().getHeldItemSlot())
				event.setCancelled(true);
			
			return;
		}
		
		if(ControlManager.isControlSomeone(player)) {
			Player controlled = ControlManager.getControlledPlayer(player);
			controlled.getInventory().setHeldItemSlot(event.getNewSlot());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onFly(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();

		if (ControlManager.isControlSomeone(player)) {
			Player controlled = ControlManager.getControlledPlayer(player);
			controlled.setAllowFlight(player.getAllowFlight());
			controlled.setFlying(event.isFlying());
		} else if(ControlManager.isControlled(player)) {
			Player controller = ControlManager.getControllerPlayer(player);
			player.setAllowFlight(controller.getAllowFlight());
			player.setFlying(controller.isFlying());
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onDie(PlayerDeathEvent event) {
		Player player = event.getEntity();
		ControlManager.remove(player);
	}

	@EventHandler(ignoreCancelled = true)
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		ControlManager.remove(player);
	}
}
