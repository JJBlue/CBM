package essentials.listeners.debugstick.entity;

import org.bukkit.attribute.Attributable;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;

public class DebugStickNextEntityStates {
	public void Blocks() {
		Attributable attributable;
		LivingEntity livingEntity;
		Damageable damageable;
		Ageable ageable;
		Tameable tameable;
		Sittable sittable;
	}
	
	public static void setNext(Entity entity, DebugStickEntityChanges type, boolean next) { //or bevore
		switch (type) {
			
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
