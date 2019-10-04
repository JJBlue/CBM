package essentials.modules.troll.control;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import essentials.modules.collision.CollisionManager;
import essentials.modules.visible.HideState;
import essentials.modules.visible.VisibleManager;

public class ControlListener implements Listener {
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent e) {
		if (ControlManager.control.containsValue(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}

		if (ControlManager.control.containsKey(e.getPlayer())) {
			Location to = e.getTo();
			if (to == null) return;
			ControlManager.control.get(e.getPlayer()).teleport(e.getTo());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onSneak(PlayerToggleSneakEvent e) {
		if (ControlManager.control.containsValue(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}

		if (ControlManager.control.containsKey(e.getPlayer())) {
			ControlManager.control.get(e.getPlayer()).setSneaking(e.getPlayer().isSneaking());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onFly(PlayerToggleFlightEvent e) {
		if (ControlManager.control.containsValue(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}

		if (ControlManager.control.containsKey(e.getPlayer())) {
			ControlManager.control.get(e.getPlayer()).setFlying(e.getPlayer().isFlying());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onSwim(EntityToggleSwimEvent e) {
		if (!(e.getEntity() instanceof Player)) return;

		Player p = (Player) e.getEntity();
		if (ControlManager.control.containsValue(p)) {
			e.setCancelled(true);
			return;
		}

		if (ControlManager.control.containsKey(p)) {
			ControlManager.control.get(p).setSwimming(p.isSwimming());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onGlide(EntityToggleGlideEvent e) {
		if (!(e.getEntity() instanceof Player)) return;

		Player p = (Player) e.getEntity();
		if (ControlManager.control.containsValue(p)) {
			e.setCancelled(true);
			return;
		}

		if (ControlManager.control.containsKey(p)) {
			ControlManager.control.get(p).setGliding(p.isGliding());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();

		for (Player p : ControlManager.control.keySet()) {
			if (!p.equals(player) && !ControlManager.control.get(p).equals(player)) continue;

			Player k = ControlManager.control.get(p);
			if (k.equals(player)) { //ControlManager.controlling player still ingame
				VisibleManager.setVisible(p, HideState.VISIBLE);
				CollisionManager.setCollision(p, true);
				player = p;
			} else if (p.equals(player)) {
				VisibleManager.setVisible(player, HideState.VISIBLE);
				CollisionManager.setCollision(player, true);
				player = k;
			}
		}

		ControlManager.control.remove(player);
	}
}
