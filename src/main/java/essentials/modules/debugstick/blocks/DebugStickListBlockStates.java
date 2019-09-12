package essentials.modules.debugstick.blocks;

import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;

import java.util.LinkedList;
import java.util.List;

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
	public static List<DebugStickBlockChanges> getBlockStates(BlockData blockData) {
		List<DebugStickBlockChanges> list = new LinkedList<>();

		if (blockData instanceof Ageable)
			list.add(DebugStickBlockChanges.AGE);
		if (blockData instanceof AnaloguePowerable)
			list.add(DebugStickBlockChanges.ANALOGUE_POWERABLE);
		if (blockData instanceof Attachable)
			list.add(DebugStickBlockChanges.ATTACH);
		if (blockData instanceof Bisected)
			list.add(DebugStickBlockChanges.BISECTED);
		if (blockData instanceof Directional)
			list.add(DebugStickBlockChanges.DIRECTIONAL);
		if (blockData instanceof Levelled)
			list.add(DebugStickBlockChanges.LEVELLED);
		if (blockData instanceof Lightable)
			list.add(DebugStickBlockChanges.LIGHT);
		if (blockData instanceof MultipleFacing) {
			list.add(DebugStickBlockChanges.MULTIPLEFACING_EAST);
			list.add(DebugStickBlockChanges.MULTIPLEFACING_WEST);
			list.add(DebugStickBlockChanges.MULTIPLEFACING_SOUTH);
			list.add(DebugStickBlockChanges.MULTIPLEFACING_NORTH);
		}
		if (blockData instanceof Openable)
			list.add(DebugStickBlockChanges.OPEN);
		if (blockData instanceof Orientable)
			list.add(DebugStickBlockChanges.ORIENT);
		if (blockData instanceof Powerable)
			list.add(DebugStickBlockChanges.POWER);
		if (blockData instanceof Rail)
			list.add(DebugStickBlockChanges.RAIL);
		if (blockData instanceof Rotatable)
			list.add(DebugStickBlockChanges.ROTATE);
		if (blockData instanceof Snowable)
			list.add(DebugStickBlockChanges.SNOW);
		if (blockData instanceof Waterlogged)
			list.add(DebugStickBlockChanges.WATERLOGGED);

		//##############################################################################################
		if (blockData instanceof Bamboo)
			list.add(DebugStickBlockChanges.LEAVES);
		if (blockData instanceof Bed)
			list.add(DebugStickBlockChanges.PART);
		if (blockData instanceof Bell)
			list.add(DebugStickBlockChanges.ATTACHMENT);
		if (blockData instanceof BrewingStand) {
			list.add(DebugStickBlockChanges.BOTTLE_0);
			list.add(DebugStickBlockChanges.BOTTLE_1);
			list.add(DebugStickBlockChanges.BOTTLE_2);
		}
		if (blockData instanceof BubbleColumn)
			list.add(DebugStickBlockChanges.DRAG);
		if (blockData instanceof Cake)
			list.add(DebugStickBlockChanges.BITES);
		if (blockData instanceof Campfire)
			list.add(DebugStickBlockChanges.SIGNAL_FIRE);
		if (blockData instanceof Chest)
			list.add(DebugStickBlockChanges.TYPE);
		if (blockData instanceof CommandBlock)
			list.add(DebugStickBlockChanges.CONDITIONAL);
		if (blockData instanceof Comparator)
			list.add(DebugStickBlockChanges.MODE);

		if (blockData instanceof DaylightDetector)
			list.add(DebugStickBlockChanges.INVERTED);
		if (blockData instanceof Dispenser)
			list.add(DebugStickBlockChanges.TRIGGERED);
		if (blockData instanceof Door)
			list.add(DebugStickBlockChanges.HINGE);
		if (blockData instanceof Farmland)
			list.add(DebugStickBlockChanges.MOISTURE);
		if (blockData instanceof Gate)
			list.add(DebugStickBlockChanges.IN_WALL);
		if (blockData instanceof Hopper)
			list.add(DebugStickBlockChanges.ENABLED);
		if (blockData instanceof Lantern)
			list.add(DebugStickBlockChanges.HANGING);
		if (blockData instanceof Leaves) {
			list.add(DebugStickBlockChanges.DISTANCE);
			list.add(DebugStickBlockChanges.PERSISTENT);
		}
		if (blockData instanceof NoteBlock) {
			list.add(DebugStickBlockChanges.INSTRUMENT);
		}
		if (blockData instanceof Piston)
			list.add(DebugStickBlockChanges.EXTENDED);

		if (blockData instanceof PistonHead)
			list.add(DebugStickBlockChanges.SHORT);
		if (blockData instanceof RedstoneWire) {
			list.add(DebugStickBlockChanges.FACE_NORTH);
			list.add(DebugStickBlockChanges.FACE_EAST);
			list.add(DebugStickBlockChanges.FACE_SOUTH);
			list.add(DebugStickBlockChanges.FACE_WEST);
		}
		if (blockData instanceof Repeater) {
			list.add(DebugStickBlockChanges.DELAY);
			list.add(DebugStickBlockChanges.LOCKED);
		}
		if (blockData instanceof Sapling)
			list.add(DebugStickBlockChanges.STAGE);
		if (blockData instanceof Scaffolding) {
			list.add(DebugStickBlockChanges.BOTTOM);
			list.add(DebugStickBlockChanges.DISTANCE);
		}
		if (blockData instanceof SeaPickle)
			list.add(DebugStickBlockChanges.PICKLES);
		if (blockData instanceof Slab)
			list.add(DebugStickBlockChanges.TYPE);
		if (blockData instanceof Snow)
			list.add(DebugStickBlockChanges.LAYERS);
		if (blockData instanceof Stairs)
			list.add(DebugStickBlockChanges.SHAPE);
		if (blockData instanceof StructureBlock)
			list.add(DebugStickBlockChanges.MODE);

		if (blockData instanceof Switch)
			list.add(DebugStickBlockChanges.FACE);
		if (blockData instanceof TechnicalPiston)
			list.add(DebugStickBlockChanges.TYPE);
		if (blockData instanceof TNT)
			list.add(DebugStickBlockChanges.UNSTABLE);
		if (blockData instanceof Tripwire)
			list.add(DebugStickBlockChanges.DISARMED);
		if (blockData instanceof TurtleEgg) {
			list.add(DebugStickBlockChanges.EGGS);
			list.add(DebugStickBlockChanges.HATCH);
		}

		return list;
	}
}
