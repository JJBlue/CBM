package essentials.listeners.debugstick.blocks;

import org.bukkit.Axis;
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
import org.bukkit.block.data.Rail.Shape;
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
	 * 	NoteBlock
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
	
	public void Blocks() {
		Bed bed;
		bed.setPart(arg0);
		
		Bell bell;
		bell.setAttachment(arg0);
		
		BrewingStand brewingStand;
		brewingStand.setBottle(arg0, arg1);
		
		Chest chest;
		chest.setType(arg0);
		
		Comparator comparator;
		comparator.setMode(arg0);
		
		Door door;
		door.setHinge(arg0);
		
		NoteBlock noteBlock;
		noteBlock.setInstrument(arg0);
		noteBlock.setNote(arg0);
		
		RedstoneWire redstoneWire;
		redstoneWire.setFace(arg0, arg1);
		
		Repeater repeater; //TODO EASY
		repeater.setDelay(arg0);
		repeater.setLocked(arg0);
		
		Sapling sapling;
		sapling.setStage(arg0);
		
		Slab slab;
		slab.setType(arg0);
		
		Stairs stairs;
		stairs.setShape(arg0);
		
		StructureBlock structureBlock;
		structureBlock.setMode(arg0);
		
		Switch switch2;
		switch2.setFace(arg0);
	}
	
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
				
				int count = 0;
				BlockFace[] blockFaces = BlockFace.values();
				for(BlockFace face : blockFaces) {
					if(face.equals(directional.getFacing()))
						break;
					count++;
				}
				
				directional.setFacing(blockFaces[nextInt(count, blockFaces.length - 1, next)]);
				
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
				
				multipleFacing.setFace(BlockFace.EAST, multipleFacing.hasFace(BlockFace.EAST));
				
				break;
			case MULTIPLEFACING_NORTH:
				if(!(blockData instanceof MultipleFacing)) break;
				multipleFacing = (MultipleFacing) blockData;
				
				multipleFacing.setFace(BlockFace.NORTH, multipleFacing.hasFace(BlockFace.NORTH));
				
				break;
			case MULTIPLEFACING_SOUTH:
				if(!(blockData instanceof MultipleFacing)) break;
				multipleFacing = (MultipleFacing) blockData;
				
				multipleFacing.setFace(BlockFace.SOUTH, multipleFacing.hasFace(BlockFace.SOUTH));
				
				break;
			case MULTIPLEFACING_WEST:
				if(!(blockData instanceof MultipleFacing)) break;
				multipleFacing = (MultipleFacing) blockData;
				
				multipleFacing.setFace(BlockFace.WEST, multipleFacing.hasFace(BlockFace.WEST));
				
				break;
			case OPEN:
				if(!(blockData instanceof Openable)) break;
				Openable openable = (Openable) blockData;
				
				openable.setOpen(!openable.isOpen());
				
				break;
			case ORIENT:
				if(!(blockData instanceof Orientable)) break;
				Orientable orientable = (Orientable) blockData;
				
				switch (orientable.getAxis()) {
					case X:
						orientable.setAxis(Axis.Y);
						break;
					case Y:
						orientable.setAxis(Axis.Z);
						break;
					case Z:
						orientable.setAxis(Axis.X);
						break;
				}
				
				break;
			case POWER:
				if(!(blockData instanceof Powerable)) break;
				Powerable powerable = (Powerable) blockData;
				
				powerable.setPowered(!powerable.isPowered());
				
				break;
			case RAIL:
				if(!(blockData instanceof Rail)) break;
				Rail rail = (Rail) blockData;
				
				switch (rail.getShape()) {
					case ASCENDING_EAST:
						rail.setShape(Shape.ASCENDING_NORTH);
						break;
					case ASCENDING_NORTH:
						rail.setShape(Shape.ASCENDING_SOUTH);
						break;
					case ASCENDING_SOUTH:
						rail.setShape(Shape.ASCENDING_WEST);
						break;
					case ASCENDING_WEST:
						rail.setShape(Shape.EAST_WEST);
						break;
					case EAST_WEST:
						rail.setShape(Shape.NORTH_EAST);
						break;
					case NORTH_EAST:
						rail.setShape(Shape.NORTH_SOUTH);
						break;
					case NORTH_SOUTH:
						rail.setShape(Shape.NORTH_WEST);
						break;
					case NORTH_WEST:
						rail.setShape(Shape.SOUTH_EAST);
						break;
					case SOUTH_EAST:
						rail.setShape(Shape.SOUTH_WEST);
						break;
					case SOUTH_WEST:
						rail.setShape(Shape.ASCENDING_EAST);
						break;
				}
				
				break;
			case ROTATE:
				if(!(blockData instanceof Rotatable)) break;
				Rotatable rotatable = (Rotatable) blockData;
				
				count = 0;
				blockFaces = BlockFace.values();
				for(BlockFace face : blockFaces) {
					if(face.equals(rotatable.getRotation()))
						break;
					count++;
				}
				
				rotatable.setRotation(blockFaces[nextInt(count, blockFaces.length - 1, next)]);

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
				break;
			case BITES:
				if(!(blockData instanceof Cake)) break;
				Cake cake = (Cake) blockData;
				
				cake.setBites(nextInt(cake.getBites(), cake.getMaximumBites(), next));
				
				break;
			case BOTTLE:
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
				break;
			case HANGING:
				if(!(blockData instanceof Lantern)) break;
				Lantern lantern = (Lantern) blockData;
				
				lantern.setHanging(!lantern.isHanging());
				
				break;
			case HATCH:
				if(!(blockData instanceof TurtleEgg)) break;
				TurtleEgg turtleEgg = (TurtleEgg) blockData;
				
				turtleEgg.setHatch(nextInt(turtleEgg.getEggs(), turtleEgg.getMinimumEggs(), turtleEgg.getMaximumEggs(), next));
				
				break;
			case HINGE:
				break;
			case INSTRUMENT:
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
				
				count = 0;
				Bamboo.Leaves[] bamboo_leaves = Bamboo.Leaves.values();
				for(Bamboo.Leaves enumValue : bamboo_leaves) {
					if(enumValue.equals(bamboo.getLeaves()))
						break;
					count++;
				}
				
				bamboo.setLeaves(bamboo_leaves[nextInt(count, bamboo_leaves.length - 1, next)]);
				
				break;
			case LOCKED:
				break;
			case MODE:
				break;
			case MOISTURE:
				if(!(blockData instanceof Farmland)) break;
				Farmland farmland = (Farmland) blockData;
				
				farmland.setMoisture(nextInt(farmland.getMoisture(), farmland.getMaximumMoisture(), next));
				
				break;
			case NOTE:
				break;
			case PART:
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
				break;
			case TRIGGERED:
				if(!(blockData instanceof Dispenser)) break;
				Dispenser dispenser = (Dispenser) blockData;
				
				dispenser.setTriggered(!dispenser.isTriggered());
				
				break;
			case TYPE:
				if(blockData instanceof TechnicalPiston) {
					TechnicalPiston technicalPiston = (TechnicalPiston) blockData;
					
					count = 0;
					TechnicalPiston.Type[] technicalpiston_type = TechnicalPiston.Type.values();
					for(TechnicalPiston.Type enumValue : technicalpiston_type) {
						if(enumValue.equals(technicalPiston.getType()))
							break;
						count++;
					}
					
					technicalPiston.setType(technicalpiston_type[nextInt(count, technicalpiston_type.length - 1, next)]);
				}
				
				break;
			case UNSTABLE:
				if(!(blockData instanceof TNT)) break;
				TNT tnt = (TNT) blockData;
				
				tnt.setUnstable(!tnt.isUnstable());
				
				break;
		}
	}

	private static int nextInt(int mom, int max, boolean next) {
		return nextInt(mom, 0, max, next);
	}
	
	private static int nextInt(int mom, int min, int max, boolean next) {
		if(next) {
			if(mom == max)
				return min;
			else
				return mom++;
		} else {
			if(mom == min)
				return max;
			else
				return mom--;
		}
	}
}
