package essentials.listeners.debugstick.entity;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sun.tools.doclint.Entity;

import essentials.listeners.debugstick.blocks.DebugStickBlockChanges;

public class DebugStickEntities {
	//TODO
	public static void setNextEntityState(Entity entity, DebugStickBlockChanges debugStickBlockChanges, boolean next /* or bevor*/) {
		
	}
	
	public static List<DebugStickEntityChanges> getPossibleBlockStateChanges(Block block){
		return null;
	}
	
	public static Object getEntityStateValue(Entity entity, DebugStickEntityChanges debugStickEntityChanges) {
		return null;
	}
	
	public static void openBlockStateEditor(Player player, Block block) {
		
	}
}
