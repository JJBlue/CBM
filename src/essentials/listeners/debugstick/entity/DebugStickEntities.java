package essentials.listeners.debugstick.entity;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DebugStickEntities {
	public static void setNextEntityState(Entity entity, DebugStickEntityChanges type, boolean next /* or bevor*/) {
		DebugStickNextEntityStates.setNext(entity, type, next);
	}
	
	public static List<DebugStickEntityChanges> getPossibleEntityStateChanges(Entity entity){
		return DebugStickListEntityStates.getEntityStates(entity);
	}
	
	public static Object getEntityStateValue(Entity entity, DebugStickEntityChanges type) {
		return DebugStickEntityGetValue.getBlockStateValue(entity, type);
	}
	
	public static void openEntityStateEditor(Player player, Entity entity) {
		DebugStickEntityInventory.openInventory(player, entity);
	}
}
