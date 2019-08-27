package essentials.listeners.debugstick.blocks;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.Snowable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Bell;
import org.bukkit.block.data.type.BrewingStand;
import org.bukkit.block.data.type.BubbleColumn;
import org.bukkit.block.data.type.Cake;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.DaylightDetector;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.block.data.type.Lantern;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.block.data.type.Scaffolding;
import org.bukkit.block.data.type.SeaPickle;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.StructureBlock;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.TNT;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.block.data.type.Tripwire;
import org.bukkit.block.data.type.TurtleEgg;

public class DebugStickListBlockStates {
	/*	Extra BlockData set able Methods:
	 * 
	 * 	Bed
	 * 	CommandBlock
	 * 	Comparator
	 * 	DaylightDetector
	 * 	Dispenser
	 * 	Door
	 * 	Farmland
	 * 	Gate
	 * 	Hopper
	 * 	Lantern
	 * 	Leaves
	 * 	NoteBlock
	 * 	Piston
	 * 	PistonHead
	 * 	RedstoneWire
	 * 	Repeater
	 * 	Sapling
	 * 	Scaffolding
	 * 	SeaPickle
	 * 	Slab
	 * 	Snow
	 * 	Stairs
	 * 	StructureBlock
	 * 	Switch
	 * 	TechnicalPiston
	 * 	TNT
	 * 	Tripwire
	 * 	TurtleEgg
	 * 	Bamboo
	 * 	Bell
	 * 	BrewingStand
	 * 	BubbleColumn
	 * 	Cake
	 * 	Campfire
	 * 	Chest
	 */
	public static List<DebugStickBlockChanges> getBlockStates(BlockData blockData){
		List<DebugStickBlockChanges> list = new LinkedList<>();
		
		if(blockData instanceof Ageable)
			list.add(DebugStickBlockChanges.AGE);
		if(blockData instanceof AnaloguePowerable)
			list.add(DebugStickBlockChanges.ANALOGUE_POWERABLE);
		if(blockData instanceof Attachable)
			list.add(DebugStickBlockChanges.ATTACH);
		if(blockData instanceof Bisected)
			list.add(DebugStickBlockChanges.BISECTED);
		if(blockData instanceof Directional)
			list.add(DebugStickBlockChanges.DIRECTIONAL);
		if(blockData instanceof Levelled)
			list.add(DebugStickBlockChanges.LEVELLED);
		if(blockData instanceof Lightable)
			list.add(DebugStickBlockChanges.LIGHT);
		if(blockData instanceof MultipleFacing) {
			list.add(DebugStickBlockChanges.MULTIPLEFACING_EAST);
			list.add(DebugStickBlockChanges.MULTIPLEFACING_WEST);
			list.add(DebugStickBlockChanges.MULTIPLEFACING_SOUTH);
			list.add(DebugStickBlockChanges.MULTIPLEFACING_NORTH);
		}
		if(blockData instanceof Openable)
			list.add(DebugStickBlockChanges.OPEN);
		if(blockData instanceof Orientable)
			list.add(DebugStickBlockChanges.ORIENT);
		if(blockData instanceof Powerable)
			list.add(DebugStickBlockChanges.POWER);
		if(blockData instanceof Rail)
			list.add(DebugStickBlockChanges.RAIL);
		if(blockData instanceof Rotatable)
			list.add(DebugStickBlockChanges.ROTATE);
		if(blockData instanceof Snowable)
			list.add(DebugStickBlockChanges.SNOW);
		if(blockData instanceof Waterlogged)
			list.add(DebugStickBlockChanges.WATERLOGGED);
		
		//TODO
		//##############################################################################################
		if(blockData instanceof Bamboo);
		if(blockData instanceof Bed)
			list.add(DebugStickBlockChanges.PART);
		if(blockData instanceof Bell)
			list.add(DebugStickBlockChanges.ATTACHMENT);
		if(blockData instanceof BrewingStand)
			list.add(DebugStickBlockChanges.BOTTLE);
		if(blockData instanceof BubbleColumn);
		if(blockData instanceof Cake);
		if(blockData instanceof Campfire);
		if(blockData instanceof Chest)
			list.add(DebugStickBlockChanges.TYPE);
		if(blockData instanceof CommandBlock);
		if(blockData instanceof Comparator)
			list.add(DebugStickBlockChanges.MODE);
		
		if(blockData instanceof DaylightDetector);
		if(blockData instanceof Dispenser)
			list.add(DebugStickBlockChanges.TRIGGERED);
		if(blockData instanceof Door)
			list.add(DebugStickBlockChanges.HINGE);
		if(blockData instanceof Farmland)
			list.add(DebugStickBlockChanges.MOISTURE);
		if(blockData instanceof Gate);
		if(blockData instanceof Hopper);
		if(blockData instanceof Lantern)
			list.add(DebugStickBlockChanges.HANGING);
		if(blockData instanceof Leaves) {
			list.add(DebugStickBlockChanges.DISTANCE);
			list.add(DebugStickBlockChanges.PERSISTENT);
		}
		if(blockData instanceof NoteBlock) {
			list.add(DebugStickBlockChanges.INSTRUMENT);
		}
		if(blockData instanceof Piston)
			list.add(DebugStickBlockChanges.EXTENDED);
		
		if(blockData instanceof PistonHead)
			list.add(DebugStickBlockChanges.SHORT);
		if(blockData instanceof RedstoneWire);
		if(blockData instanceof Repeater) {
			list.add(DebugStickBlockChanges.DELAY);
			list.add(DebugStickBlockChanges.LOCKED);
		}
		if(blockData instanceof Sapling)
			list.add(DebugStickBlockChanges.STAGE);
		if(blockData instanceof Scaffolding)
			list.add(DebugStickBlockChanges.BOTTOM);
			list.add(DebugStickBlockChanges.DISTANCE);
		if(blockData instanceof SeaPickle)
			list.add(DebugStickBlockChanges.PICKLES);
		if(blockData instanceof Slab)
			list.add(DebugStickBlockChanges.TYPE);
		if(blockData instanceof Snow)
			list.add(DebugStickBlockChanges.LAYERS);
		if(blockData instanceof Stairs)
			list.add(DebugStickBlockChanges.SHAPE);
		if(blockData instanceof StructureBlock)
			list.add(DebugStickBlockChanges.MODE);
		
		if(blockData instanceof Switch)
			list.add(DebugStickBlockChanges.FACE);
		if(blockData instanceof TechnicalPiston)
			list.add(DebugStickBlockChanges.TYPE);
		if(blockData instanceof TNT)
			list.add(DebugStickBlockChanges.UNSTABLE);
		if(blockData instanceof Tripwire)
			list.add(DebugStickBlockChanges.DISARMED);
		if(blockData instanceof TurtleEgg)
			list.add(DebugStickBlockChanges.EGGS);
			list.add(DebugStickBlockChanges.HATCH);
		
		return list;
	}
}
