package essentials.modules.troll.control;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class ControlManager {
	protected final static HashMap<Player, Player> control = new HashMap<>();
	
	public static void add(Player controller, Player player) {
		control.put(controller, player);
	}

	public static boolean containsKey(Player p) {
		return control.containsKey(p);
	}

	public static void remove(Player p) {
		control.remove(p);
	}

	public static boolean containsValue(Player player) {
		return control.containsValue(player);
	}
}
