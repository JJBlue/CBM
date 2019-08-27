package essentials.listeners.debugstick.entity;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;

public class DebugStickListEntityStates {
	public static List<DebugStickEntityChanges> getEntityStates(Entity entity){
		List<DebugStickEntityChanges> list = new LinkedList<>();
		
		if(entity instanceof Player) {
			list.add(DebugStickEntityChanges.FLY_SPEED);
			list.add(DebugStickEntityChanges.WALK_SPEED);
		}
		
		if(entity instanceof Entity) {
			list.add(DebugStickEntityChanges.CUSTOM_NAME_VISIBLE);
			list.add(DebugStickEntityChanges.FIRE_TRICKS);
			list.add(DebugStickEntityChanges.GLOWING);
			list.add(DebugStickEntityChanges.GRAVITY);
			list.add(DebugStickEntityChanges.INVULNERABLE);
			list.add(DebugStickEntityChanges.PERSISTENT);
			list.add(DebugStickEntityChanges.SILENT);
		}
		
		if(entity instanceof LivingEntity) {
			list.add(DebugStickEntityChanges.AI);
			list.add(DebugStickEntityChanges.CAN_PICKUP_ITEMS);
			list.add(DebugStickEntityChanges.COLLIDABLE);
			list.add(DebugStickEntityChanges.GLIDING);
			list.add(DebugStickEntityChanges.REMAINING_AIR);
			list.add(DebugStickEntityChanges.REMOVE_WHEN_FAR_AWAY);
			list.add(DebugStickEntityChanges.SWIMMING);
		}
		
		if(entity instanceof Damageable) {
			list.add(DebugStickEntityChanges.HEALTH);
		}
		
		if(entity instanceof Ageable) {
			list.add(DebugStickEntityChanges.AGE_LOCK);
			list.add(DebugStickEntityChanges.BREED);
			list.add(DebugStickEntityChanges.CHANGE_AGE);
		}
		
		if(entity instanceof Tameable) {
			list.add(DebugStickEntityChanges.TAMED);
		}
		
		if(entity instanceof Sittable) {
			list.add(DebugStickEntityChanges.SITTABLE);
		}
		
		return list;
	}
}
