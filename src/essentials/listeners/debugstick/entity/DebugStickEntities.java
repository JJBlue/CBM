package essentials.listeners.debugstick.entity;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DebugStickEntities {
	//TODO
	public static void setNextEntityState(Entity entity, DebugStickEntityChanges type, boolean next /* or bevor*/) {
		DebugStickNextEntityStates.setNext(entity, type, next);
	}
	
	public static List<DebugStickEntityChanges> getPossibleEntityStateChanges(Block block){
		return null;
	}
	
	public static Object getEntityStateValue(Entity entity, DebugStickEntityChanges type) {
		return null;
	}
	
	public static void openEntityStateEditor(Player player, Block block) {
		
	}
}
