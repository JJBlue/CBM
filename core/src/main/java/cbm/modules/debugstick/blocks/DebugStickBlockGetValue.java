package cbm.modules.debugstick.blocks;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;

public class DebugStickBlockGetValue {
	public static Object getBlockStateValue(BlockData blockData, DebugStickBlockChanges type) {
		switch (type) {
			case AGE:
				if (!(blockData instanceof Ageable)) break;
				Ageable ageable = (Ageable) blockData;
				return ageable.getAge();

			case ANALOGUE_POWERABLE:
				if (!(blockData instanceof AnaloguePowerable)) break;
				AnaloguePowerable analoguePowerable = (AnaloguePowerable) blockData;
				return analoguePowerable.getPower();

			case ATTACH:
				if (!(blockData instanceof Attachable)) break;
				Attachable attachable = (Attachable) blockData;
				return attachable.isAttached();

			case BISECTED:
				if (!(blockData instanceof Bisected)) break;
				Bisected bisected = (Bisected) blockData;
				return bisected.getHalf();

			case DIRECTIONAL:
				if (!(blockData instanceof Directional)) break;
				Directional directional = (Directional) blockData;
				return directional.getFacing();

			case LEVELLED:
				if (!(blockData instanceof Levelled)) break;
				Levelled levelled = (Levelled) blockData;
				return levelled.getLevel();

			case LIGHT:
				if (!(blockData instanceof Lightable)) break;
				Lightable lightable = (Lightable) blockData;
				return lightable.isLit();

			case MULTIPLEFACING_EAST:
				if (!(blockData instanceof MultipleFacing)) break;
				MultipleFacing multipleFacing = (MultipleFacing) blockData;
				return multipleFacing.hasFace(BlockFace.EAST);

			case MULTIPLEFACING_NORTH:
				if (!(blockData instanceof MultipleFacing)) break;
				multipleFacing = (MultipleFacing) blockData;
				return multipleFacing.hasFace(BlockFace.NORTH);

			case MULTIPLEFACING_SOUTH:
				if (!(blockData instanceof MultipleFacing)) break;
				multipleFacing = (MultipleFacing) blockData;
				return multipleFacing.hasFace(BlockFace.SOUTH);

			case MULTIPLEFACING_WEST:
				if (!(blockData instanceof MultipleFacing)) break;
				multipleFacing = (MultipleFacing) blockData;
				return multipleFacing.hasFace(BlockFace.WEST);

			case OPEN:
				if (!(blockData instanceof Openable)) break;
				Openable openable = (Openable) blockData;
				return openable.isOpen();

			case ORIENT:
				if (!(blockData instanceof Orientable)) break;
				Orientable orientable = (Orientable) blockData;
				return orientable.getAxis();

			case POWER:
				if (!(blockData instanceof Powerable)) break;
				Powerable powerable = (Powerable) blockData;
				return powerable.isPowered();

			case RAIL:
				if (!(blockData instanceof Rail)) break;
				Rail rail = (Rail) blockData;
				return rail.getShape();

			case ROTATE:
				if (!(blockData instanceof Rotatable)) break;
				Rotatable rotatable = (Rotatable) blockData;
				return rotatable.getRotation();

			case SNOW:
				if (!(blockData instanceof Snowable)) break;
				Snowable snowable = (Snowable) blockData;
				return snowable.isSnowy();

			case WATERLOGGED:
				if (!(blockData instanceof Waterlogged)) break;
				Waterlogged waterlogged = (Waterlogged) blockData;
				return waterlogged.isWaterlogged();

			//##############################################################################################

			case ATTACHMENT:
				if (!(blockData instanceof Bell)) break;
				Bell bell = (Bell) blockData;
				return bell.getAttachment();

			case BITES:
				if (!(blockData instanceof Cake)) break;
				Cake cake = (Cake) blockData;
				return cake.getBites();

			case BOTTLE_0:
				if (!(blockData instanceof BrewingStand)) break;
				BrewingStand brewingStand = (BrewingStand) blockData;
				return brewingStand.hasBottle(0);

			case BOTTLE_1:
				if (!(blockData instanceof BrewingStand)) break;
				brewingStand = (BrewingStand) blockData;
				return brewingStand.hasBottle(1);

			case BOTTLE_2:
				if (!(blockData instanceof BrewingStand)) break;
				brewingStand = (BrewingStand) blockData;
				return brewingStand.hasBottle(2);

			case BOTTOM:
				if (!(blockData instanceof Scaffolding)) break;
				Scaffolding scaffolding = (Scaffolding) blockData;
				return scaffolding.isBottom();

			case CONDITIONAL:
				if (!(blockData instanceof CommandBlock)) break;
				CommandBlock commandBlock = (CommandBlock) blockData;
				return commandBlock.isConditional();

			case DELAY:
				if (!(blockData instanceof Repeater)) break;
				Repeater repeater = (Repeater) blockData;
				return repeater.getDelay();

			case DISARMED:
				if (!(blockData instanceof Tripwire)) break;
				Tripwire tripwire = (Tripwire) blockData;
				return tripwire.isDisarmed();

			case DISTANCE:
				if (blockData instanceof Leaves) {
					Leaves leaves = (Leaves) blockData;
					return leaves.getDistance();
				} else if (blockData instanceof Scaffolding) {
					scaffolding = (Scaffolding) blockData;
					return scaffolding.getDistance();
				}

				break;
			case DRAG:
				if (!(blockData instanceof BubbleColumn)) break;
				BubbleColumn bubbleColumn = (BubbleColumn) blockData;
				return bubbleColumn.isDrag();

			case EGGS:
				if (!(blockData instanceof TurtleEgg)) break;
				TurtleEgg turtleEgg = (TurtleEgg) blockData;
				return turtleEgg.getEggs();

			case ENABLED:
				if (!(blockData instanceof Hopper)) break;
				Hopper hopper = (Hopper) blockData;
				return hopper.isEnabled();

			case EXTENDED:
				if (!(blockData instanceof Piston)) break;
				Piston piston = (Piston) blockData;
				return piston.isExtended();

			case FACE:
				if (!(blockData instanceof Switch)) break;
				Switch switch1 = (Switch) blockData;
				return switch1.getAttachedFace();

			case FACE_NORTH:
				if (!(blockData instanceof RedstoneWire)) break;
				RedstoneWire redstoneWire = (RedstoneWire) blockData;
				return redstoneWire.getFace(BlockFace.NORTH);

			case FACE_EAST:
				if (!(blockData instanceof RedstoneWire)) break;
				redstoneWire = (RedstoneWire) blockData;
				return redstoneWire.getFace(BlockFace.EAST);

			case FACE_SOUTH:
				if (!(blockData instanceof RedstoneWire)) break;
				redstoneWire = (RedstoneWire) blockData;
				return redstoneWire.getFace(BlockFace.SOUTH);

			case FACE_WEST:
				if (!(blockData instanceof RedstoneWire)) break;
				redstoneWire = (RedstoneWire) blockData;
				return redstoneWire.getFace(BlockFace.WEST);

			case HANGING:
				if (!(blockData instanceof Lantern)) break;
				Lantern lantern = (Lantern) blockData;
				return lantern.isHanging();

			case HATCH:
				if (!(blockData instanceof TurtleEgg)) break;
				turtleEgg = (TurtleEgg) blockData;
				return turtleEgg.getHatch();

			case HINGE:
				if (!(blockData instanceof Door)) break;
				Door door = (Door) blockData;
				return door.getHinge();

			case INSTRUMENT:
				if (!(blockData instanceof NoteBlock)) break;
				NoteBlock noteblock = (NoteBlock) blockData;
				return noteblock.getInstrument();

			case INVERTED:
				if (!(blockData instanceof DaylightDetector)) break;
				DaylightDetector daylightDetector = (DaylightDetector) blockData;
				return daylightDetector.isInverted();

			case IN_WALL:
				if (!(blockData instanceof Gate)) break;
				Gate gate = (Gate) blockData;
				return gate.isInWall();

			case LAYERS:
				if (!(blockData instanceof Snow)) break;
				Snow snow = (Snow) blockData;
				return snow.getLayers();

			case LEAVES:
				if (!(blockData instanceof Bamboo)) break;
				Bamboo bamboo = (Bamboo) blockData;
				return bamboo.getLeaves();

			case LOCKED:
				if (!(blockData instanceof Repeater)) break;
				repeater = (Repeater) blockData;
				return repeater.isLocked();

			case MODE:
				if (blockData instanceof Comparator) {
					Comparator comparator = (Comparator) blockData;
					return comparator.getMode();
				} else if (blockData instanceof StructureBlock) {
					StructureBlock structureBlock = (StructureBlock) blockData;
					return structureBlock.getMode();
				}

				break;
			case MOISTURE:
				if (!(blockData instanceof Farmland)) break;
				Farmland farmland = (Farmland) blockData;
				return farmland.getMoisture();

			case PART:
				if (!(blockData instanceof Bed)) break;
				Bed bed = (Bed) blockData;
				return bed.getPart();

			case PERSISTENT:
				if (!(blockData instanceof Leaves)) break;
				Leaves leaves = (Leaves) blockData;
				return leaves.isPersistent();

			case PICKLES:
				if (!(blockData instanceof SeaPickle)) break;
				SeaPickle seaPickle = (SeaPickle) blockData;
				return seaPickle.getPickles();

			case SHAPE:
				if (!(blockData instanceof Stairs)) break;
				Stairs stairs = (Stairs) blockData;
				return stairs.getShape();

			case SHORT:
				if (!(blockData instanceof PistonHead)) break;
				PistonHead pistonHead = (PistonHead) blockData;
				return pistonHead.isShort();

			case SIGNAL_FIRE:
				if (!(blockData instanceof Campfire)) break;
				Campfire campfire = (Campfire) blockData;
				return campfire.isSignalFire();

			case STAGE:
				if (!(blockData instanceof Sapling)) break;
				Sapling sapling = (Sapling) blockData;
				return sapling.getStage();

			case TRIGGERED:
				if (!(blockData instanceof Dispenser)) break;
				Dispenser dispenser = (Dispenser) blockData;
				return dispenser.isTriggered();

			case TYPE:
				if (blockData instanceof TechnicalPiston) {
					TechnicalPiston technicalPiston = (TechnicalPiston) blockData;
					return technicalPiston.getType();
				} else if (blockData instanceof Chest) {
					Chest chest = (Chest) blockData;
					return chest.getType();
				} else if (blockData instanceof Slab) {
					Slab slab = (Slab) blockData;
					return slab.getType();
				}

				break;
			case UNSTABLE:
				if (!(blockData instanceof TNT)) break;
				TNT tnt = (TNT) blockData;
				return tnt.isUnstable();
		}
		return null;
	}
}
