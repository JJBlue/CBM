package cbm.modules.debugstick.entity;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Cat;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Husk;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.PufferFish;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Raider;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Spellcaster;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public class DebugStickListEntityStates {
	public static List<DebugStickEntityChanges> getEntityStates(Entity entity) {
		List<DebugStickEntityChanges> list = new LinkedList<>();

		if (entity instanceof Player) {
			list.add(DebugStickEntityChanges.FLY_SPEED);
			list.add(DebugStickEntityChanges.WALK_SPEED);
		}

		if (entity != null) {
			list.add(DebugStickEntityChanges.CUSTOM_NAME_VISIBLE);
			list.add(DebugStickEntityChanges.FIRE_TRICKS);
			list.add(DebugStickEntityChanges.GLOWING);
			list.add(DebugStickEntityChanges.GRAVITY);
			list.add(DebugStickEntityChanges.INVULNERABLE);
			list.add(DebugStickEntityChanges.PERSISTENT);
			list.add(DebugStickEntityChanges.SILENT);
		}

		if (entity instanceof LivingEntity) {
			list.add(DebugStickEntityChanges.AI);
			list.add(DebugStickEntityChanges.CAN_PICKUP_ITEMS);
			list.add(DebugStickEntityChanges.COLLIDABLE);
			list.add(DebugStickEntityChanges.GLIDING);
			list.add(DebugStickEntityChanges.REMAINING_AIR);
			list.add(DebugStickEntityChanges.REMOVE_WHEN_FAR_AWAY);
			list.add(DebugStickEntityChanges.SWIMMING);
		}

		if (entity instanceof Damageable)
			list.add(DebugStickEntityChanges.HEALTH);

		if (entity instanceof Ageable) {
			list.add(DebugStickEntityChanges.AGE_LOCK);
			list.add(DebugStickEntityChanges.BREED);
			list.add(DebugStickEntityChanges.CHANGE_AGE);
		}

		if (entity instanceof Tameable)
			list.add(DebugStickEntityChanges.TAMED);

		if (entity instanceof Sittable)
			list.add(DebugStickEntityChanges.SITTABLE);

		//#################################################################################
		if (entity instanceof PigZombie) {
			list.add(DebugStickEntityChanges.ANGER);
			list.add(DebugStickEntityChanges.ANGRY);
			list.add(DebugStickEntityChanges.CONVERSION_TIME);
		}

		if (entity instanceof Wolf) {
			list.add(DebugStickEntityChanges.ANGRY);
			list.add(DebugStickEntityChanges.COLLAR_COLOR);
		}

		if (entity instanceof Painting)
			list.add(DebugStickEntityChanges.ART);

		if (entity instanceof Bat)
			list.add(DebugStickEntityChanges.AWAKE);

		if (entity instanceof TropicalFish) {
			list.add(DebugStickEntityChanges.BODY_COLOR);
			list.add(DebugStickEntityChanges.PATTERN_COLOR);
			list.add(DebugStickEntityChanges.PATTERN);
		}

		if (entity instanceof Projectile)
			list.add(DebugStickEntityChanges.BOUNCE);

		if (entity instanceof ChestedHorse)
			list.add(DebugStickEntityChanges.CARRYING_CHEST);

		if (entity instanceof Zombie) {
			list.add(DebugStickEntityChanges.CHANGE_AGE);
			list.add(DebugStickEntityChanges.CONVERSION_TIME);
		}

		if (entity instanceof WitherSkull)
			list.add(DebugStickEntityChanges.CHARGED);

		if (entity instanceof Vex)
			list.add(DebugStickEntityChanges.CHARGING);

		if (entity instanceof Cat) {
			list.add(DebugStickEntityChanges.COLLAR_COLOR);
			list.add(DebugStickEntityChanges.TYPE);
		}

		if (entity instanceof Horse) {
			list.add(DebugStickEntityChanges.COLOR);
			list.add(DebugStickEntityChanges.STYLE);
		}

		if (entity instanceof Llama) {
			list.add(DebugStickEntityChanges.COLOR);
			list.add(DebugStickEntityChanges.STRENGTH);
		}

		if (entity instanceof Husk)
			list.add(DebugStickEntityChanges.CONVERSION_TIME);

		if (entity instanceof AbstractArrow) {
			list.add(DebugStickEntityChanges.CRITICAL);
			list.add(DebugStickEntityChanges.DAMAGE);
			list.add(DebugStickEntityChanges.PICKUP_STATUS);
			list.add(DebugStickEntityChanges.PIERCE_LEVEL);
		}

		if (entity instanceof Fox) {
			list.add(DebugStickEntityChanges.CROUCHING);
			list.add(DebugStickEntityChanges.SLEEPING);
			list.add(DebugStickEntityChanges.TYPE);
		}

		if (entity instanceof Minecart) {
			list.add(DebugStickEntityChanges.DAMAGE);
			list.add(DebugStickEntityChanges.DISPLAY_BLOCK_OFFSET);
			list.add(DebugStickEntityChanges.MAXSPEED);
			list.add(DebugStickEntityChanges.SLOW_WHEN_EMPTY);
		}

		if (entity instanceof Snowman)
			list.add(DebugStickEntityChanges.DERP);

		if (entity instanceof EnderSignal) {
			list.add(DebugStickEntityChanges.DESPAWN_TIMER);
			list.add(DebugStickEntityChanges.DROP_ITEM);
		}

		if (entity instanceof AbstractHorse) {
			list.add(DebugStickEntityChanges.DOMESTICATION);
			list.add(DebugStickEntityChanges.JUMP_STRENGTH);
		}

		if (entity instanceof FallingBlock) {
			list.add(DebugStickEntityChanges.DROP_ITEM);
			list.add(DebugStickEntityChanges.HURT_ENTITIES);
		}

		if (entity instanceof ExperienceOrb) {
			list.add(DebugStickEntityChanges.EXPERIENCE);
		}

		if (entity instanceof Creeper) {
			list.add(DebugStickEntityChanges.EXPLOSION_RADIUS);
			list.add(DebugStickEntityChanges.MAX_FUSE_TICKS);
			list.add(DebugStickEntityChanges.POWERED);
		}

		if (entity instanceof TNTPrimed) {
			list.add(DebugStickEntityChanges.FUSE_TICKS);
		}

		if (entity instanceof SpectralArrow)
			list.add(DebugStickEntityChanges.GLOWING_TICKS);

		if (entity instanceof Panda) {
			list.add(DebugStickEntityChanges.HIDDEN_GENE);
			list.add(DebugStickEntityChanges.MAIN_GENE);
		}

		if (entity instanceof Explosive) {
			list.add(DebugStickEntityChanges.IS_INCENDIARY);
			list.add(DebugStickEntityChanges.YIELD);
		}

		if (entity instanceof Boat) {
			list.add(DebugStickEntityChanges.MAXSPEED);
			list.add(DebugStickEntityChanges.OCCUPIED_DECELERATION);
			list.add(DebugStickEntityChanges.TYPE);
			list.add(DebugStickEntityChanges.WORK_ON_LAND);
		}

		if (entity instanceof Raider)
			list.add(DebugStickEntityChanges.PATROL_LEADER);

		if (entity instanceof Item)
			list.add(DebugStickEntityChanges.PICKUP_DELAY);

		if (entity instanceof IronGolem)
			list.add(DebugStickEntityChanges.PLAYER_CREATED);

		if (entity instanceof Villager) {
			list.add(DebugStickEntityChanges.PROFESSION);
			list.add(DebugStickEntityChanges.SLEEPING);
			list.add(DebugStickEntityChanges.TYPE);
			list.add(DebugStickEntityChanges.VILLAGER_EXPERIENCE);
			list.add(DebugStickEntityChanges.VILLAGER_LEVEL);
		}

		if (entity instanceof PufferFish)
			list.add(DebugStickEntityChanges.PUFF_STATE);

		if (entity instanceof Pig)
			list.add(DebugStickEntityChanges.SADDLE);

		if (entity instanceof Sheep)
			list.add(DebugStickEntityChanges.SHEARED);

		if (entity instanceof Firework)
			list.add(DebugStickEntityChanges.SHOT_AT_ANGLE);

		if (entity instanceof EnderCrystal)
			list.add(DebugStickEntityChanges.SHOWING_BOTTOM);

		if (entity instanceof Slime)
			list.add(DebugStickEntityChanges.SIZE);

		if (entity instanceof Phantom)
			list.add(DebugStickEntityChanges.SIZE);

		if (entity instanceof Spellcaster)
			list.add(DebugStickEntityChanges.SPELL);

		if (entity instanceof Rabbit)
			list.add(DebugStickEntityChanges.TYPE);

		if (entity instanceof MushroomCow)
			list.add(DebugStickEntityChanges.VARIANT);

		if (entity instanceof Parrot)
			list.add(DebugStickEntityChanges.VARIANT);
		
		list.add(DebugStickEntityChanges.LOCATION_X);
		list.add(DebugStickEntityChanges.LOCATION_Y);
		list.add(DebugStickEntityChanges.LOCATION_Z);
		list.add(DebugStickEntityChanges.PITCH);
		list.add(DebugStickEntityChanges.YAW);

		return list;
	}
}
