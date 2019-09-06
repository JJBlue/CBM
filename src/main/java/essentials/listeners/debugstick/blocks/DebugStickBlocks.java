package essentials.listeners.debugstick.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.List;

public class DebugStickBlocks {
	public static void setNextBlockState(Block block, DebugStickBlockChanges debugStickBlockChanges, boolean next /* or bevor*/) {
		BlockData blockData = block.getBlockData();
		setNextBlockState(blockData, debugStickBlockChanges, next);
		block.setBlockData(blockData);
	}
	
	public static void setNextBlockState(BlockData blockData, DebugStickBlockChanges debugStickBlockChanges, boolean next /* or bevor*/) {
		DebugStickNextBlockStates.setNext(blockData, debugStickBlockChanges, next);
	}
	
	public static List<DebugStickBlockChanges> getPossibleBlockStateChanges(Block block){
		return getPossibleBlockStateChanges(block.getBlockData());
	}
	
	public static List<DebugStickBlockChanges> getPossibleBlockStateChanges(BlockData blockData){
		return DebugStickListBlockStates.getBlockStates(blockData);
	}
	
	public static Object getBlockDataValue(Block block, DebugStickBlockChanges debugStickBlockChanges) {
		return DebugStickBlockGetValue.getBlockStateValue(block.getBlockData(), debugStickBlockChanges);
	}
	
	public static Object getBlockDataValue(BlockData blockData, DebugStickBlockChanges debugStickBlockChanges) {
		return DebugStickBlockGetValue.getBlockStateValue(blockData, debugStickBlockChanges);
	}
	
	public static void openBlockStateEditor(Player player, Block block) {
		DebugStickBlockInventory.openInventory(player, block);
	}
}
