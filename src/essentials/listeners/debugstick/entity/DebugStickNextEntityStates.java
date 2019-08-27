package essentials.listeners.debugstick.entity;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;

public class DebugStickNextEntityStates {
	/*
	 * Age Zombie etc not working
	 */
	@SuppressWarnings("deprecation")
	public static void setNext(Entity entity, DebugStickEntityChanges type, boolean next) { //or bevore		
		if(entity instanceof Player) {
			Player player = (Player) entity;
			
			switch (type) {
				case FLY_SPEED:
					player.setFlySpeed((float) nextDouble(player.getFlySpeed(), 10, next));
					break;
				case WALK_SPEED:
					player.setWalkSpeed((float) nextDouble(player.getWalkSpeed(), 10, next));
					break;
				default:
					break;
			}
		}
		
		switch(type) {
			case CUSTOM_NAME_VISIBLE:
				entity.setCustomNameVisible(!entity.isCustomNameVisible());
				break;
			case FIRE_TRICKS:
				entity.setFireTicks(nextInt(entity.getFireTicks(), entity.getMaxFireTicks(), next));
				break;
			case GLOWING:
				entity.setGlowing(!entity.isGlowing());
				break;
			case GRAVITY:
				entity.setGravity(!entity.hasGravity());
				break;
			case INVULNERABLE:
				entity.setInvulnerable(!entity.isInvulnerable());
				break;
			case PERSISTENT:
				entity.setPersistent(!entity.isPersistent());
				break;
			case SILENT:
				entity.setSilent(!entity.isSilent());
				break;
			default:
				break;
		}
		
		if(entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			
			switch(type) {
				case AI:
					livingEntity.setAI(!livingEntity.hasAI());
					break;
				case CAN_PICKUP_ITEMS:
					livingEntity.setCanPickupItems(!livingEntity.getCanPickupItems());
					break;
				case COLLIDABLE:
					livingEntity.setCollidable(!livingEntity.isCollidable());
					break;
				case GLIDING:
					livingEntity.setGliding(!livingEntity.isGliding());
					break;
				case REMAINING_AIR:
					livingEntity.setRemainingAir(nextInt(livingEntity.getRemainingAir(), livingEntity.getMaximumAir(), next));
					break;
				case REMOVE_WHEN_FAR_AWAY:
					livingEntity.setRemoveWhenFarAway(!livingEntity.getRemoveWhenFarAway());
					break;
				case SWIMMING:
					livingEntity.setSwimming(!livingEntity.isSwimming());
					break;
				default:
					break;
			}
		}
		
		if(entity instanceof Damageable) {
			Damageable damageable = (Damageable) entity;
			
			switch (type) {
				case HEALTH:
					if(!(entity instanceof Attributable)) return;
					Attributable attributable = (Attributable) entity;
					
					damageable.setHealth(nextDouble(damageable.getHealth(), attributable.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), next));
					break;
				default:
					break;
			}
		}
		
		if(entity instanceof Ageable) {
			Ageable ageable = (Ageable) entity;
			
			switch (type) {
				case AGE_LOCK:
					ageable.setAgeLock(!ageable.getAgeLock());
					break;
				case BREED:
					ageable.setBreed(!ageable.canBreed());
					break;
				case CHANGE_AGE:
					if(ageable.isAdult())
						ageable.setBaby();
					else
						ageable.setAdult();
					break;
				default:
					break;
			}
		}
		
		if(entity instanceof Tameable) {
			Tameable tameable = (Tameable) entity;
			
			switch (type) {
				case TAMED:
					tameable.setTamed(!tameable.isTamed());
					break;
				default:
					break;
			}
		}
		
		if(entity instanceof Sittable) {
			Sittable sittable = (Sittable) entity;
			
			switch (type) {
				case SITTABLE:
					sittable.setSitting(!sittable.isSitting());
					break;
				default:
					break;
			}
		}
	}

	private static double nextDouble(double mom, double max, boolean next) {
		return nextDouble(mom, 0, max, next);
	}
	
	private static double nextDouble(double mom, double min, double max, boolean next) {
		if(next) {
			if(mom == max)
				return min;
			else
				return mom += 0.5;
		} else {
			if(mom == min)
				return max;
			else
				return mom -= 0.5;
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
