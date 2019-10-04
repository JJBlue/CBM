package essentials.modules.troll.control;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import essentials.modules.collision.CollisionManager;
import essentials.modules.visible.HideState;
import essentials.modules.visible.VisibleManager;
import essentials.utilities.player.EnumHandUtil;
import essentials.utilities.player.PlayerUtilities;

public class ControlListener implements Listener {
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
	
	@EventHandler(ignoreCancelled = true)
	public void armSwing(PlayerAnimationEvent event) {
		Player player = event.getPlayer();
		
		if(ControlManager.isControlled(player)) {
			event.setCancelled(true);
			return;
		}
		
		if(ControlManager.isControlSomeone(player)) {
			Player controlled = ControlManager.getControlledPlayer(player);
			PlayerUtilities.setArmSwing(controlled, EnumHandUtil.MAIN_HAND);
		}
	}
	
	@EventHandler
	public void changeSlot(PlayerItemHeldEvent event) { //TODO check
		Player player = event.getPlayer();
		
		if(ControlManager.isControlled(player)) {
			event.setCancelled(true);
			return;
		}
		
		if(ControlManager.isControlSomeone(player)) {
			Player controlled = ControlManager.getControlledPlayer(player);
			PlayerUtilities.setHeldItemSlot(controlled, player.getInventory().getHeldItemSlot());
		}
	}

	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player player = e.getPlayer();
		
		if (ControlManager.isControlled(player)) {
			player.setSneaking(ControlManager.getControllerPlayer(player).isSneaking());
			e.setCancelled(true);
			return;
		}

		if (ControlManager.isControlSomeone(player))
			ControlManager.getControlledPlayer(player).setSneaking(player.isSneaking());
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
	public void onSwim(EntityToggleSwimEvent e) { //TODO
		if (!(e.getEntity() instanceof Player)) return;

		Player p = (Player) e.getEntity();
		if (ControlManager.isControlled(p)) {
			e.setCancelled(true);
			return;
		}

		if (ControlManager.isControlSomeone(p))
			ControlManager.getControlledPlayer(p).setSwimming(p.isSwimming());
	}

	@EventHandler(ignoreCancelled = true)
	public void onGlide(EntityToggleGlideEvent e) { //TODO
		if (!(e.getEntity() instanceof Player)) return;

		Player p = (Player) e.getEntity();
		if (ControlManager.isControlled(p)) {
			e.setCancelled(true);
			return;
		}

		if (ControlManager.isControlSomeone(p))
			ControlManager.getControlledPlayer(p).setGliding(p.isGliding());
	}

	@EventHandler(ignoreCancelled = true)
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();

		if(ControlManager.isControlled(player)) {
			Player controller = ControlManager.getControllerPlayer(player);
			
			CollisionManager.setCollision(controller, true);
			VisibleManager.setVisible(controller, HideState.VISIBLE);
		} else {
			CollisionManager.setCollision(player, true);
			VisibleManager.setVisible(player, HideState.VISIBLE);
		}
		
		ControlManager.remove(player);
	}
}
