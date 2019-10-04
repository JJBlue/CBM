package essentials.modules.troll.control;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class ControlManager {
	protected final static HashMap<Player, Player> control = new HashMap<>();
	protected final static HashMap<Player, Player> controlledMap = new HashMap<>();
	
	public static void add(Player controller, Player controlled) {
		control.put(controller, controlled);
		controlledMap.put(controlled, controller);
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
