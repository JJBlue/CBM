package essentials.listeners.debugstick.entity;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;

public class DebugStickEntityGetValue {
	@SuppressWarnings("deprecation")
	public static Object getBlockStateValue(Entity entity, DebugStickEntityChanges type) {
		if(entity instanceof Player) {
			Player player = (Player) entity;
			
			switch (type) {
				case FLY_SPEED:
					return player.getFlySpeed();
				case WALK_SPEED:
					return player.getWalkSpeed();
				default:
					break;
			}
		}
		
		if(entity instanceof Entity) {
			Entity livingEntity = (Entity) entity;
			
			switch(type) {
				case CUSTOM_NAME_VISIBLE:
					return livingEntity.isCustomNameVisible();
				case FIRE_TRICKS:
					return livingEntity.getFireTicks();
				case GLOWING:
					return livingEntity.isGlowing();
				case GRAVITY:
					return livingEntity.hasGravity();
				case INVULNERABLE:
					return livingEntity.isInvulnerable();
				case PERSISTENT:
					return livingEntity.isPersistent();
				case SILENT:
					return livingEntity.isSilent();
				default:
					break;
			}
		}
		
		if(entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			
			switch(type) {
				case AI:
					return livingEntity.hasAI();
				case CAN_PICKUP_ITEMS:
					return livingEntity.getCanPickupItems();
				case COLLIDABLE:
					return livingEntity.isCollidable();
				case GLIDING:
					return livingEntity.isGliding();
				case REMAINING_AIR:
					return livingEntity.getRemainingAir();
				case REMOVE_WHEN_FAR_AWAY:
					return livingEntity.getRemoveWhenFarAway();
				case SWIMMING:
					return livingEntity.isSwimming();
				default:
					break;
			}
		}
		
		if(entity instanceof Damageable) {
			Damageable damageable = (Damageable) entity;
			
			switch (type) {
				case HEALTH:
					return damageable.getHealth();
				default:
					break;
			}
		}
		
		if(entity instanceof Ageable) {
			Ageable ageable = (Ageable) entity;
			
			switch (type) {
				case AGE_LOCK:
					return ageable.getAgeLock();
				case BREED:
					return ageable.canBreed();
				case CHANGE_AGE:
					return ageable.isAdult();
				default:
					break;
			}
		}
		
		if(entity instanceof Tameable) {
			Tameable tameable = (Tameable) entity;
			
			switch (type) {
				case TAMED:
					return tameable.isTamed();
				default:
					break;
			}
		}
		
		if(entity instanceof Sittable) {
			Sittable sittable = (Sittable) entity;
			
			switch (type) {
				case SITTABLE:
					return sittable.isSitting();
				default:
					break;
			}
		}
		
		return null;
	}
}
