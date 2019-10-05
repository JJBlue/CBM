package essentials.modules.troll.control;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import essentials.utilities.player.PlayerUtilities;

public class ControlListener implements Listener {
	Set<Event> events = Collections.synchronizedSet(new HashSet<>());
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void block(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if(ControlManager.isControlSomeone(player)) {
			event.setCancelled(true);
			Event e = new BlockBreakEvent(event.getBlock(), ControlManager.getControlledPlayer(player));
			events.add(e);
			Bukkit.getPluginManager().callEvent(e);
		} else if(ControlManager.isControlled(player)) {
			if(events.contains(event))
				events.remove(event);
			else
				event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void block(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		
		if(ControlManager.isControlSomeone(player)) {
			event.setCancelled(true);
			Event e = new BlockPlaceEvent(event.getBlockPlaced(), event.getBlockReplacedState(), event.getBlockAgainst(), event.getItemInHand(), ControlManager.getControlledPlayer(player), event.canBuild(), event.getHand());
			events.add(e);
			Bukkit.getPluginManager().callEvent(e);
		} else if(ControlManager.isControlled(player)) {
			if(events.contains(event))
				events.remove(event);
			else
				event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void chat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if(ControlManager.isControlSomeone(player)) {
			event.setCancelled(true);
			ControlManager.getControlledPlayer(player).chat(event.getMessage());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void entity(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player)) return;
		
		Player player = (Player) event.getDamager();
		
		if(ControlManager.isControlSomeone(player)) {
			event.setCancelled(true);
			Event e = new EntityDamageByEntityEvent(ControlManager.getControlledPlayer(player), event.getEntity(), event.getCause(), event.getDamage());
			events.add(e);
			Bukkit.getPluginManager().callEvent(e);
		} else if(ControlManager.isControlled(player)) {
			if(events.contains(event))
				events.remove(event);
			else
				event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void interact(PlayerInteractAtEntityEvent event) {
		Player player = (Player) event.getPlayer();
		
		if(ControlManager.isControlSomeone(player)) {
			event.setCancelled(true);
			Event e = new PlayerInteractAtEntityEvent(ControlManager.getControlledPlayer(player), event.getRightClicked(), event.getClickedPosition(), event.getHand());
			events.add(e);
			Bukkit.getPluginManager().callEvent(e);
		} else if(ControlManager.isControlled(player)) {
			if(events.contains(event))
				events.remove(event);
			else
				event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void interact(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		
		if(ControlManager.isControlSomeone(player)) {
			event.setCancelled(true);
			Bukkit.getPluginManager().callEvent(
				new PlayerInteractEvent(
					ControlManager.getControlledPlayer(player),
					event.getAction(),
					event.getItem(),
					event.getClickedBlock(),
					event.getBlockFace(),
					event.getHand()
				)
			);
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
	public void changeSlot(PlayerItemHeldEvent event) { //TODO test
		Player player = event.getPlayer();
		
		if(ControlManager.isControlled(player)) {
			Player controller = ControlManager.getControllerPlayer(player);
			if(event.getNewSlot() != controller.getInventory().getHeldItemSlot())
				event.setCancelled(true);
			
			return;
		}
		
		if(ControlManager.isControlSomeone(player)) {
			Player controlled = ControlManager.getControlledPlayer(player);
			PlayerUtilities.setHeldItemSlot(controlled, player.getInventory().getHeldItemSlot());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onFly(PlayerToggleFlightEvent e) {
		if (ControlManager.isControlled(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}

		if (ControlManager.isControlSomeone(e.getPlayer()))
			ControlManager.getControlledPlayer(e.getPlayer()).setFlying(e.getPlayer().isFlying());
	}

	@EventHandler(ignoreCancelled = true)
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		ControlManager.remove(player);
	}
}
