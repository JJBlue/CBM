package essentials.listeners.debugstick.blocks;

import java.util.Set;

import org.bukkit.Instrument;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
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

public class DebugStickNextBlockStates {
	/*	Extra BlockData set able Methods:
	 * 
	 * 	Bamboo
	 * 	Bed
	 * 	Bell
	 * 	BrewingStand
	 * 	BubbleColumn
	 * 	Cake
	 * 	Campfire
	 * 	Chest
	 * 	CommandBlock
	 * 	Comparator
	 * 
	 * 	DaylightDetector
	 * 	Dispenser
	 * 	Door
	 * 	Farmland
	 * 	Gate
	 * 	Hopper
	 * 	Lantern
	 * 	Leaves
	 * 	NoteBlock -> (Future: Editor)
	 * 	Piston
	 * 
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
	 * 
	 * 	Switch
	 * 	TechnicalPiston
	 * 	TNT
	 * 	Tripwire
	 * 	TurtleEgg
	 */
	
	/*
	 * 	TODO better:
	 * 	NoteBlock note
	 * 
	 */
	
	public static void setNext(BlockData blockData, DebugStickBlockChanges type, boolean next) { //or bevore
		switch (type) {
			case AGE:
				if(!(blockData instanceof Ageable)) break;
				Ageable ageable = (Ageable) blockData;
				
				ageable.setAge(nextInt(ageable.getAge(), ageable.getMaximumAge(), next));
				
				break;
			case ANALOGUE_POWERABLE:
				if(!(blockData instanceof AnaloguePowerable)) break;
				AnaloguePowerable analoguePowerable = (AnaloguePowerable) blockData;
				
				analoguePowerable.setPower(nextInt(analoguePowerable.getPower(), analoguePowerable.getMaximumPower(), next));
				
				break;
			case ATTACH:
				if(!(blockData instanceof Attachable)) break;
				Attachable attachable = (Attachable) blockData;
				
				attachable.setAttached(!attachable.isAttached());
				
				break;
			case BISECTED:
				if(!(blockData instanceof Bisected)) break;
				Bisected bisected = (Bisected) blockData;
				
				switch(bisected.getHalf()) {
					case BOTTOM:
						bisected.setHalf(Half.TOP);
						break;
					case TOP:
						bisected.setHalf(Half.BOTTOM);
						break;
				}
				
				break;
			case DIRECTIONAL:
				if(!(blockData instanceof Directional)) break;
				Directional directional = (Directional) blockData;
				
				directional.setFacing(nextSet(directional.getFacing(), directional.getFaces()));
				
				break;
			case LEVELLED:
				if(!(blockData instanceof Levelled)) break;
				Levelled levelled = (Levelled) blockData;
				
				levelled.setLevel(nextInt(levelled.getLevel(), levelled.getMaximumLevel(), next));
				
				break;
			case LIGHT:
				if(!(blockData instanceof Lightable)) break;
				Lightable lightable = (Lightable) blockData;
				
				lightable.setLit(!lightable.isLit());

				break;
			case MULTIPLEFACING_EAST:
				if(!(blockData instanceof MultipleFacing)) break;
				MultipleFacing multipleFacing = (MultipleFacing) blockData;
				
				multipleFacing.setFace(BlockFace.EAST, !multipleFacing.hasFace(BlockFace.EAST));
				
				break;
			case MULTIPLEFACING_NORTH:
				if(!(blockData instanceof MultipleFacing)) break;
				multipleFacing = (MultipleFacing) blockData;
				
				multipleFacing.setFace(BlockFace.NORTH, !multipleFacing.hasFace(BlockFace.NORTH));
				
				break;
			case MULTIPLEFACING_SOUTH:
				if(!(blockData instanceof MultipleFacing)) break;
				multipleFacing = (MultipleFacing) blockData;
				
				multipleFacing.setFace(BlockFace.SOUTH, !multipleFacing.hasFace(BlockFace.SOUTH));
				
				break;
			case MULTIPLEFACING_WEST:
				if(!(blockData instanceof MultipleFacing)) break;
				multipleFacing = (MultipleFacing) blockData;
				
				multipleFacing.setFace(BlockFace.WEST, !multipleFacing.hasFace(BlockFace.WEST));
				
				break;
			case OPEN:
				if(!(blockData instanceof Openable)) break;
				Openable openable = (Openable) blockData;
				
				openable.setOpen(!openable.isOpen());
				
				break;
			case ORIENT:
				if(!(blockData instanceof Orientable)) break;
				Orientable orientable = (Orientable) blockData;
				
				orientable.setAxis(nextSet(orientable.getAxis(), orientable.getAxes()));
				
				break;
			case POWER:
				if(!(blockData instanceof Powerable)) break;
				Powerable powerable = (Powerable) blockData;
				
				powerable.setPowered(!powerable.isPowered());
				
				break;
			case RAIL:
				if(!(blockData instanceof Rail)) break;
				Rail rail = (Rail) blockData;
				
				rail.setShape(nextSet(rail.getShape(), rail.getShapes()));
				
				break;
			case ROTATE:
				if(!(blockData instanceof Rotatable)) break;
				Rotatable rotatable = (Rotatable) blockData;
				
				int count = 0;
				BlockFace[] blockFaces = getBlockFaces();
				for(BlockFace face : blockFaces) {
					if(face.equals(rotatable.getRotation()))
						break;
					count++;
				}
				
				int start = count;
				do {
					count = nextInt(count, blockFaces.length - 1, next);
					try {
						rotatable.setRotation(blockFaces[count]);
						break;
					} catch (IllegalArgumentException | IllegalStateException e) {}
				} while(start != count);
				
				break;
			case SNOW:
				if(!(blockData instanceof Snowable)) break;
				Snowable snowable = (Snowable) blockData;
				
				snowable.setSnowy(!snowable.isSnowy());
				
				break;
			case WATERLOGGED:
				if(!(blockData instanceof Waterlogged)) break;
				Waterlogged waterlogged = (Waterlogged) blockData;
				
				waterlogged.setWaterlogged(!waterlogged.isWaterlogged());
				
				break;
			//##############################################################################################
				
			case ATTACHMENT:
				if(!(blockData instanceof Bell)) break;
				Bell bell = (Bell) blockData;
				
				bell.setAttachment(nextPosition(bell.getAttachment(), next, Bell.Attachment.values()));
				
				break;
			case BITES:
				if(!(blockData instanceof Cake)) break;
				Cake cake = (Cake) blockData;
				
				cake.setBites(nextInt(cake.getBites(), cake.getMaximumBites(), next));
				
				break;
			case BOTTLE_0:
				if(!(blockData instanceof BrewingStand)) break;
				BrewingStand brewingStand = (BrewingStand) blockData;
				
				brewingStand.setBottle(0, !brewingStand.hasBottle(0));
				
				break;
			case BOTTLE_1:
				if(!(blockData instanceof BrewingStand)) break;
				brewingStand = (BrewingStand) blockData;
				
				brewingStand.setBottle(1, !brewingStand.hasBottle(1));
				
				break;
			case BOTTLE_2:
				if(!(blockData instanceof BrewingStand)) break;
				brewingStand = (BrewingStand) blockData;
				
				brewingStand.setBottle(2, !brewingStand.hasBottle(2));
				
				break;
			case BOTTOM:
				if(!(blockData instanceof Scaffolding)) break;
				Scaffolding scaffolding = (Scaffolding) blockData;
				
				scaffolding.setBottom(!scaffolding.isBottom());
				
				break;
			case CONDITIONAL:
				if(!(blockData instanceof CommandBlock)) break;
				CommandBlock commandBlock = (CommandBlock) blockData;
				
				commandBlock.setConditional(!commandBlock.isConditional());
				
				break;
			case DELAY:
				if(!(blockData instanceof Repeater)) break;
				Repeater repeater = (Repeater) blockData;
				
				repeater.setDelay(nextInt(repeater.getDelay(), repeater.getMinimumDelay(), repeater.getMaximumDelay(), next));
				
				break;
			case DISARMED:
				if(!(blockData instanceof Tripwire)) break;
				Tripwire tripwire = (Tripwire) blockData;
				
				tripwire.setDisarmed(!tripwire.isDisarmed());
				
				break;
			case DISTANCE:
				if(blockData instanceof Leaves) {
					Leaves leaves = (Leaves) blockData;
					leaves.setDistance(nextInt(leaves.getDistance(), 10, next));
				} else if(blockData instanceof  Scaffolding){
					scaffolding = (Scaffolding) blockData;
					scaffolding.setDistance(nextInt(scaffolding.getDistance(), scaffolding.getMaximumDistance(), next));
				}
				
				break;
			case DRAG:
				if(!(blockData instanceof BubbleColumn)) break;
				BubbleColumn bubbleColumn = (BubbleColumn) blockData;
				
				bubbleColumn.setDrag(!bubbleColumn.isDrag());
				
				break;
			case EGGS:
				if(!(blockData instanceof TurtleEgg)) break;
				TurtleEgg turtleEgg = (TurtleEgg) blockData;

				turtleEgg.setEggs(nextInt(turtleEgg.getEggs(), turtleEgg.getMinimumEggs(), turtleEgg.getMaximumEggs(), next));
				
				break;
			case ENABLED:
				if(!(blockData instanceof Hopper)) break;
				Hopper hopper = (Hopper) blockData;
				
				hopper.setEnabled(!hopper.isEnabled());
				
				break;
			case EXTENDED:
				if(!(blockData instanceof Piston)) break;
				Piston piston = (Piston) blockData;

				piston.setExtended(!piston.isExtended());
				break;
			case FACE:
				if(!(blockData instanceof Switch)) break;
				Switch switch1 = (Switch) blockData;
				
				switch1.setFace(nextPosition(switch1.getFace(), next, Switch.Face.values()));
				
				break;
			case FACE_NORTH:
				if(!(blockData instanceof RedstoneWire)) break;
				RedstoneWire redstoneWire = (RedstoneWire) blockData;
				
				redstoneWire.setFace(BlockFace.NORTH, nextPosition(redstoneWire.getFace(BlockFace.NORTH), next, RedstoneWire.Connection.values()));
				
				break;
			case FACE_EAST:
				if(!(blockData instanceof RedstoneWire)) break;
				redstoneWire = (RedstoneWire) blockData;
				
				redstoneWire.setFace(BlockFace.EAST, nextPosition(redstoneWire.getFace(BlockFace.EAST), next, RedstoneWire.Connection.values()));
				
				break;
			case FACE_SOUTH:
				if(!(blockData instanceof RedstoneWire)) break;
				redstoneWire = (RedstoneWire) blockData;

				redstoneWire.setFace(BlockFace.SOUTH, nextPosition(redstoneWire.getFace(BlockFace.SOUTH), next, RedstoneWire.Connection.values()));
				
				break;
			case FACE_WEST:
				if(!(blockData instanceof RedstoneWire)) break;
				redstoneWire = (RedstoneWire) blockData;
				
				redstoneWire.setFace(BlockFace.WEST, nextPosition(redstoneWire.getFace(BlockFace.WEST), next, RedstoneWire.Connection.values()));
				
				break;
			case HANGING:
				if(!(blockData instanceof Lantern)) break;
				Lantern lantern = (Lantern) blockData;
				
				lantern.setHanging(!lantern.isHanging());
				
				break;
			case HATCH:
				if(!(blockData instanceof TurtleEgg)) break;
				turtleEgg = (TurtleEgg) blockData;
				
				turtleEgg.setHatch(nextInt(turtleEgg.getHatch(), turtleEgg.getMaximumHatch(), next));
				
				break;
			case HINGE:
				if(!(blockData instanceof Door)) break;
				Door door = (Door) blockData;
				
				door.setHinge(nextPosition(door.getHinge(), next, Door.Hinge.values()));
				
				break;
			case INSTRUMENT:
				if(!(blockData instanceof NoteBlock)) break;
				NoteBlock noteblock = (NoteBlock) blockData;
				
				noteblock.setInstrument(nextPosition(noteblock.getInstrument(), next, Instrument.values()));
				
				break;
			case INVERTED:
				if(!(blockData instanceof DaylightDetector)) break;
				DaylightDetector daylightDetector = (DaylightDetector) blockData;
				
				daylightDetector.setInverted(!daylightDetector.isInverted());
				
				break;
			case IN_WALL:
				if(!(blockData instanceof Gate)) break;
				Gate gate = (Gate) blockData;
				
				gate.setInWall(!gate.isInWall());
				
				break;
			case LAYERS:
				if(!(blockData instanceof Snow)) break;
				Snow snow = (Snow) blockData;
				
				snow.setLayers(nextInt(snow.getLayers(), snow.getMinimumLayers(), snow.getMaximumLayers(), next));
				
				break;
			case LEAVES:
				if(!(blockData instanceof Bamboo)) break;
				Bamboo bamboo = (Bamboo) blockData;
				
				bamboo.setLeaves(nextPosition(bamboo.getLeaves(), next, Bamboo.Leaves.values()));
				
				break;
			case LOCKED:
				if(!(blockData instanceof Repeater)) break;
				repeater = (Repeater) blockData;
				
				repeater.setLocked(!repeater.isLocked());
				
				break;
			case MODE:
				if(blockData instanceof Comparator) {
					Comparator comparator = (Comparator) blockData;
					comparator.setMode(nextPosition(comparator.getMode(), next, Comparator.Mode.values()));
				} else if(blockData instanceof StructureBlock) {
					StructureBlock structureBlock = (StructureBlock) blockData;
					structureBlock.setMode(nextPosition(structureBlock.getMode(), next, StructureBlock.Mode.values()));
				}
				
				break;
			case MOISTURE:
				if(!(blockData instanceof Farmland)) break;
				Farmland farmland = (Farmland) blockData;
				
				farmland.setMoisture(nextInt(farmland.getMoisture(), farmland.getMaximumMoisture(), next));
				
				break;
			case PART:
				if(!(blockData instanceof Bed)) break;
				Bed bed = (Bed) blockData;
				
				bed.setPart(nextPosition(bed.getPart(), next, Bed.Part.values()));
				
				break;				
			case PERSISTENT:
				if(!(blockData instanceof Leaves)) break;
				Leaves leaves = (Leaves) blockData;
				
				leaves.setPersistent(!leaves.isPersistent());
				
				break;
			case PICKLES:
				if(!(blockData instanceof SeaPickle)) break;
				SeaPickle seaPickle = (SeaPickle) blockData;

				seaPickle.setPickles(nextInt(seaPickle.getPickles(), seaPickle.getMinimumPickles(), seaPickle.getMaximumPickles(), next));

				break;
			case SHAPE:
				if(!(blockData instanceof Stairs)) break;
				Stairs stairs = (Stairs) blockData;

				stairs.setShape(nextPosition(stairs.getShape(), next, Stairs.Shape.values()));
				
				break;
			case SHORT:
				if(!(blockData instanceof PistonHead)) break;
				PistonHead pistonHead = (PistonHead) blockData;

				pistonHead.setShort(!pistonHead.isShort());

				break;
			case SIGNAL_FIRE:
				if(!(blockData instanceof Campfire)) break;
				Campfire campfire = (Campfire) blockData;
				
				campfire.setSignalFire(!campfire.isSignalFire());
				
				break;
			case STAGE:
				if(!(blockData instanceof Sapling)) break;
				Sapling sapling = (Sapling) blockData;
				
				sapling.setStage(nextInt(sapling.getStage(), sapling.getMaximumStage(), next));
				
				break;
			case TRIGGERED:
				if(!(blockData instanceof Dispenser)) break;
				Dispenser dispenser = (Dispenser) blockData;
				
				dispenser.setTriggered(!dispenser.isTriggered());
				
				break;
			case TYPE:
				if(blockData instanceof TechnicalPiston) {
					TechnicalPiston technicalPiston = (TechnicalPiston) blockData;
					technicalPiston.setType(nextPosition(technicalPiston.getType(), next, TechnicalPiston.Type.values())); //TODO
				} else if(blockData instanceof Chest) {
					Chest chest = (Chest) blockData;
					chest.setType(nextPosition(chest.getType(), next, Chest.Type.values()));
				} else if(blockData instanceof Slab) {
					Slab slab = (Slab) blockData;
					slab.setType(nextPosition(slab.getType(), next, Slab.Type.values()));
				}
				
				break;
			case UNSTABLE:
				if(!(blockData instanceof TNT)) break;
				TNT tnt = (TNT) blockData;
				
				tnt.setUnstable(!tnt.isUnstable());
				
				break;
		}
	}
	
	private static BlockFace[] getBlockFaces() {
		BlockFace[] blockFaces = new BlockFace[BlockFace.values().length];
		
		int index = 0;
		blockFaces[index++] = BlockFace.NORTH;
		
		blockFaces[index++] = BlockFace.NORTH_NORTH_EAST;
		blockFaces[index++] = BlockFace.NORTH_EAST;
		blockFaces[index++] = BlockFace.EAST_NORTH_EAST;
		
		blockFaces[index++] = BlockFace.EAST;
		
		blockFaces[index++] = BlockFace.EAST_SOUTH_EAST;
		blockFaces[index++] = BlockFace.SOUTH_EAST;
		blockFaces[index++] = BlockFace.SOUTH_SOUTH_EAST;
		
		blockFaces[index++] = BlockFace.SOUTH;
		
		blockFaces[index++] = BlockFace.SOUTH_SOUTH_WEST;
		blockFaces[index++] = BlockFace.SOUTH_WEST;
		blockFaces[index++] = BlockFace.WEST_SOUTH_WEST;
		
		blockFaces[index++] = BlockFace.WEST;
		
		blockFaces[index++] = BlockFace.WEST_NORTH_WEST;
		blockFaces[index++] = BlockFace.NORTH_WEST;
		blockFaces[index++] = BlockFace.NORTH_NORTH_WEST;
		
		blockFaces[index++] = BlockFace.UP;
		blockFaces[index++] = BlockFace.DOWN;
		blockFaces[index++] = BlockFace.SELF;
		
		return blockFaces;
	}
	
	@SafeVarargs
	private static <T extends Enum<T>> T nextPosition(T currentEnum, boolean next, T... enums) {
		int count = 0;
		for(T enumValue : enums) {
			if(enumValue.equals(currentEnum))
				break;
			count++;
		}
		return enums[nextInt(count, enums.length - 1, next)];
	}
	
	private static <T> T nextSet(T current, Set<T> sets) {
		T first = null;
		boolean found = false;
		
		for(T obj : sets) {
			if(first == null)
				first = obj;
			
			if(obj.equals(current))
				found = true;
			else if(found)
				return obj;
		}
		
		return first;
	}

	private static int nextInt(int mom, int max, boolean next) {
		return nextInt(mom, 0, max, next);
	}
	
	private static int nextInt(int mom, int min, int max, boolean next) {
		if(next) {
			if(mom == max)
				return min;
			else
				return ++mom;
		} else {
			if(mom == min)
				return max;
			else
				return --mom;
		}
	}
}
