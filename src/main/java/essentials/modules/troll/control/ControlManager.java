package essentials.modules.troll.control;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import essentials.main.Main;
import essentials.modules.collision.CollisionManager;
import essentials.modules.disguise.DisguiseManager;
import essentials.modules.visible.HideState;
import essentials.modules.visible.VisibleManager;

public class ControlManager {
	protected final static HashMap<Player, Player> control = new HashMap<>();
	protected final static HashMap<Player, Player> controlledMap = new HashMap<>();
	
	public static void add(Player controller, Player controlled) {
		remove(controller);
		
		control.put(controller, controlled);
		controlledMap.put(controlled, controller);
		
		VisibleManager.setVisible(controller, HideState.VISIBLE);
		DisguiseManager.disguise(controller, controlled.getName());
		
		VisibleManager.setVisible(controlled, HideState.INVISIBLE);
		CollisionManager.setCollision(controlled, false);
		controlled.hidePlayer(Main.getPlugin(), controller);
		
		controller.teleport(controlled.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
	}

	public static void remove(Player p) {
		if(isControlSomeone(p)) {
			Player controlled = control.remove(p);
			controlledMap.remove(controlled);
			
			DisguiseManager.undisguise(p);
			VisibleManager.setVisible(p, HideState.INVISIBLE);
			
			VisibleManager.setVisible(controlled, HideState.VISIBLE);
		}
		
		if(isControlled(p)) {
			Player controller = controlledMap.remove(p);
			control.remove(controller);
			
			DisguiseManager.undisguise(controller);
			VisibleManager.setVisible(controller, HideState.INVISIBLE);
			
			VisibleManager.setVisible(p, HideState.VISIBLE);
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
