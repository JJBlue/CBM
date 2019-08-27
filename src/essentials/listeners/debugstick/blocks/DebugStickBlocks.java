package essentials.listeners.debugstick.blocks;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

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
}
