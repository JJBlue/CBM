package essentials.modules.troll.controlCopy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
	@EventHandler(priority = EventPriority.LOWEST)
	public void block(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if(ControlManager.isControlSomeone(player)) {
			event.setCancelled(true);
			Bukkit.getPluginManager().callEvent(new BlockBreakEvent(event.getBlock(), ControlManager.getControlledPlayer(player)));
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void block(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		
		if(ControlManager.isControlSomeone(player)) {
			event.setCancelled(true);
			Bukkit.getPluginManager().callEvent(new BlockPlaceEvent(event.getBlockPlaced(), event.getBlockReplacedState(), event.getBlockAgainst(), event.getItemInHand(), ControlManager.getControlledPlayer(player), event.canBuild(), event.getHand()));
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
			Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(ControlManager.getControlledPlayer(player), event.getEntity(), event.getCause(), event.getDamage()));
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void interact(PlayerInteractAtEntityEvent event) {
		Player player = (Player) event.getPlayer();
		
		if(ControlManager.isControlSomeone(player)) {
			event.setCancelled(true);
			Bukkit.getPluginManager().callEvent(new PlayerInteractAtEntityEvent(ControlManager.getControlledPlayer(player), event.getRightClicked(), event.getClickedPosition(), event.getHand()));
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
