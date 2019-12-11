package essentials.modules.troll.control;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import essentials.main.Main;
import essentials.modules.disguise.DisguiseManager;
import essentials.modules.visible.HideState;
import essentials.modules.visible.VisibleManager;

public class ControlManager {
	protected final static Map<Player, Player> control = Collections.synchronizedMap(new HashMap<>());
	protected final static Map<Player, Player> controlledMap = Collections.synchronizedMap(new HashMap<>());
	
	protected final static Map<Player, ItemStack[]> inventories = Collections.synchronizedMap(new HashMap<>());
	
	public static void add(Player controller, Player controlled) {
		remove(controller);
		
		control.put(controller, controlled);
		controlledMap.put(controlled, controller);
		
		VisibleManager.setVisible(controller, HideState.VISIBLE);
		DisguiseManager.disguise(controller, controlled.getName());
		setInventory(controller, controlled);
		
		VisibleManager.setVisible(controlled, HideState.INVISIBLE_FOR_ALL);
		controlled.hidePlayer(Main.getPlugin(), controller);
		controlled.setCanPickupItems(false);
		controlled.setAllowFlight(controller.getAllowFlight());
		controlled.setFlying(controller.isFlying());
		
		controller.teleport(controlled.getLocation(), PlayerTeleportEvent.TeleportCause.SPECTATE);
	}
	
	private static void setInventory(Player player, Player toPlayer) {
		ItemStack[] contents = player.getInventory().getContents();
		inventories.put(player, contents);
		player.getInventory().setContents(toPlayer.getInventory().getContents());
	}
	
	private static ItemStack[] setOldInventory(Player player) {
		ItemStack[] contents = inventories.remove(player);
		if(contents == null) return player.getInventory().getContents();
		ItemStack[] current = player.getInventory().getContents();
		player.getInventory().setContents(contents);
		return current;
	}

	public static void remove(Player p) {
		Player controller = null;
		Player controlled = null;
		
		if(isControlSomeone(p)) {
			controlled = control.remove(p);
			controlledMap.remove(controlled);
			controller = p;
		}
		
		if(isControlled(p)) {
			controller = controlledMap.remove(p);
			control.remove(controller);
			controlled = p;
		}
		
		if(controller != null && controlled != null) {
			DisguiseManager.undisguise(controller);
			VisibleManager.setVisible(controller, HideState.INVISIBLE);
			VisibleManager.sendMessage(controller);
			ItemStack[] contents = setOldInventory(controller);
			
			VisibleManager.setVisible(controlled, HideState.VISIBLE);
			controlled.setCanPickupItems(true);
			controlled.getInventory().setContents(contents);
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
