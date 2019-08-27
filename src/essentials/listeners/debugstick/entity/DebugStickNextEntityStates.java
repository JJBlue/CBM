package essentials.listeners.debugstick.entity;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.type.Snow;
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
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Husk;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
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
import org.bukkit.entity.Skeleton;
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
import org.bukkit.entity.ZombieVillager;

public class DebugStickNextEntityStates {
	/*
	 * Age Zombie etc not working
	 */
	public void Blocks() {
		AbstractHorse abstractHorse;
		abstractHorse.setVariant(arg0);
		abstractHorse.setDomestication(arg0);
		abstractHorse.setJumpStrength(arg0);
		
		Bat bat;
		bat.setAwake(arg0);
		
		Boat boat;
		boat.setWoodType(arg0);
		boat.setMaxSpeed(arg0);
		boat.setOccupiedDeceleration(arg0);
		boat.setWorkOnLand(arg0);
		
		Cat cat;
		cat.setCatType(arg0);
		cat.setCollarColor(arg0);
		
		ChestedHorse chestedHorse;
		chestedHorse.setCarryingChest(arg0);
		
		Creeper creeper;
		creeper.setPowered(arg0);
		creeper.setExplosionRadius(arg0);
		creeper.setMaxFuseTicks(arg0);
		
		EnderCrystal enderCrystal;
		enderCrystal.setShowingBottom(arg0);
		
		EnderSignal enderSignal;
		enderSignal.setDropItem(arg0);
		enderSignal.setDespawnTimer(arg0);
		
		ExperienceOrb experienceOrb;
		experienceOrb.setExperience(arg0);
		
		Explosive explosive;
		explosive.setYield(arg0);
		explosive.setIsIncendiary(arg0);
		
		FallingBlock fallingBlock;
		fallingBlock.setDropItem(arg0);
		fallingBlock.setHurtEntities(arg0);
		
		Firework firework;
		firework.setShotAtAngle(arg0);
		
		FishHook fishHook;
		fishHook.setBiteChance(arg0);
		
		Fox fox;
		fox.setFoxType(arg0);
		fox.setCrouching(arg0);
		fox.setSleeping(arg0);
		
		Guardian guardian;
		guardian.setElder(arg0);
		
		Horse horse;
		horse.setColor(arg0);
		horse.setStyle(arg0);
		
		Husk husk;
		husk.setConversionTime(arg0);
		
		IronGolem ironGolem;
		ironGolem.setPlayerCreated(arg0);
		
		Item item;
		item.setPickupDelay(arg0);
		
		Llama llama;
		llama.setColor(arg0);
		llama.setStrength(arg0);
		
		Minecart minecart;
		minecart.setDamage(arg0);
		minecart.setMaxSpeed(arg0);
		minecart.setSlowWhenEmpty(arg0);
		minecart.setDisplayBlockOffset(arg0);
		
		MushroomCow mushroomCow;
		mushroomCow.setVariant(arg0);
		
		Ocelot ocelot;
		ocelot.setCatType(arg0);
		
		Painting painting;
		painting.setArt(arg0);
		
		Panda panda;
		panda.setMainGene(arg0);
		panda.setHiddenGene(arg0);
		
		Parrot parrot;
		parrot.setVariant(arg0);
		
		Phantom phantom;
		phantom.setSize(arg0);
		
		Pig pig;
		pig.setSaddle(arg0);
		
		PigZombie pigZombie;
		pigZombie.setAnger(arg0);
		pigZombie.setAngry(arg0);
		pigZombie.setConversionTime(arg0);
		
		Projectile projectile;
		projectile.setBounce(arg0);
		
		PufferFish pufferFish;
		pufferFish.setPuffState(arg0);
		
		Rabbit rabbit;
		rabbit.setRabbitType(arg0);
		
		Raider raider;
		raider.setPatrolLeader(arg0);
		
		Sheep sheep;
		sheep.setSheared(arg0);
		
		Skeleton skeleton;
		skeleton.setSkeletonType(arg0);
		
		Slime slime;
		slime.setSize(arg0);
		
		Snowman snowman;
		snowman.setDerp(arg0);
		
		SpectralArrow spectralArrow;
		spectralArrow.setGlowingTicks(arg0);
		
		Spellcaster spellcaster;
		spellcaster.setSpell(arg0);
		
		TNTPrimed tntPrimed;
		tntPrimed.setFuseTicks(arg0);
		
		TropicalFish tropicalFish;
		tropicalFish.setPatternColor(arg0);
		tropicalFish.setBodyColor(arg0);
		tropicalFish.setPattern(arg0);
		
		Vex vex;
		vex.setCharging(arg0);
		
		Villager villager;
		villager.setProfession(arg0);
		villager.setVillagerType(arg0);
		villager.setVillagerLevel(arg0);
		villager.setVillagerExperience(arg0);
		villager.wakeup();
		
		WitherSkull witherSkull;
		witherSkull.setCharged(arg0);
		
		Wolf wolf;
		wolf.setAngry(arg0);
		wolf.setCollarColor(arg0);
		
		Zombie zombie;
		zombie.setBaby(arg0);
		zombie.setConversionTime(arg0);
		
		AbstractArrow abstractArrow;
		abstractArrow.setDamage(arg0);
		abstractArrow.setPierceLevel(arg0);
		abstractArrow.setCritical(arg0);
		abstractArrow.setPickupStatus(arg0);
	}
	
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
