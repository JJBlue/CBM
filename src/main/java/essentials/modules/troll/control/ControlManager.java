package essentials.modules.troll.control;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import essentials.modules.collision.CollisionManager;
import essentials.modules.visible.HideState;
import essentials.modules.visible.VisibleManager;

public class ControlManager {
	protected final static HashMap<Player, Player> control = new HashMap<>();
	protected final static HashMap<Player, Player> controlledMap = new HashMap<>();
	
	public static void add(Player controller, Player controlled) {
		if(control.containsKey(controller)) return;
		if(controlledMap.containsKey(controlled)) return;
		
		control.put(controller, controlled);
		controlledMap.put(controlled, controller);
		
		VisibleManager.setVisible(controller, HideState.INVISIBLE);
		CollisionManager.setCollision(controller, false);
		controller.teleport(controlled.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
	}

	public static void remove(Player p) {
		if(control.containsKey(p)) {
			Player controlled = control.remove(p);
			controlledMap.remove(controlled);
		}
		
		if(controlledMap.containsKey(p)) {
			Player controlled = controlledMap.remove(p);
			control.remove(controlled);
		}
	}
	
	public static Player getControlledPlayer(Player player) {
		return control.get(player);
	}
	
	public static Player getControllerPlayer(Player player) {
		return controlledMap.get(player);
	}
	
	public static boolean isControlSomeone(Player p) {
		return control.containsKey(p);
	}

	public static boolean isControlled(Player player) {
		return control.containsValue(player);
	}
}
