package essentials.listeners.debugstick.entity;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;

public class DebugStickNextEntityStates {
	public void Blocks() {
		Attributable attributable;
		Attribute attribute = Attribute.GENERIC_ARMOR;
		switch (attribute) {
			case GENERIC_ARMOR:
				break;
			case GENERIC_ARMOR_TOUGHNESS:
				break;
			case GENERIC_ATTACK_DAMAGE:
				break;
			case GENERIC_ATTACK_SPEED:
				break;
			case GENERIC_FLYING_SPEED:
				break;
			case GENERIC_FOLLOW_RANGE:
				break;
			case GENERIC_KNOCKBACK_RESISTANCE:
				break;
			case GENERIC_LUCK:
				break;
			case GENERIC_MOVEMENT_SPEED:
				break;
			case HORSE_JUMP_STRENGTH:
				break;
			case ZOMBIE_SPAWN_REINFORCEMENTS:
				break;
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public static void setNext(Entity entity, DebugStickEntityChanges type, boolean next) { //or bevore
		if(entity instanceof Entity) {
			Entity livingEntity = (Entity) entity;
			
			switch(type) {
				case CUSTOM_NAME_VISIBLE:
					livingEntity.setCustomNameVisible(!livingEntity.isCustomNameVisible());
					break;
				case FIRE_TRICKS:
					livingEntity.setFireTicks(nextInt(livingEntity.getFireTicks(), livingEntity.getMaxFireTicks(), next));
					break;
				case GLOWING:
					livingEntity.setGlowing(!livingEntity.isGlowing());
					break;
				case GRAVITY:
					livingEntity.setGravity(!livingEntity.hasGravity());
					break;
				case INVULNERABLE:
					livingEntity.setInvulnerable(!livingEntity.isInvulnerable());
					break;
				case PERSISTENT:
					livingEntity.setPersistent(!livingEntity.isPersistent());
					break;
				case SILENT:
					livingEntity.setSilent(!livingEntity.isSilent());
					break;
				default:
					break;
			}
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
