package essentials.listeners.debugstick.entity;

import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.TreeSpecies;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
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

public class DebugStickNextEntityStates {
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
		
		switch (type) {
			case ANGER:
				if(!(entity instanceof PigZombie)) break;
				PigZombie pigZombie = (PigZombie) entity;
				pigZombie.setAnger(nextInt(pigZombie.getAnger(), 20, next));
				
				break;
			case ANGRY:
				if(entity instanceof PigZombie) {
					pigZombie = (PigZombie) entity;
					pigZombie.setAngry(!pigZombie.isAngry());
				} else if(entity instanceof Wolf) {
					Wolf wolf = (Wolf) entity;
					wolf.setAngry(!wolf.isAngry());
				}
				
				break;
			case ART:
				if(!(entity instanceof Painting)) break;
				Painting painting = (Painting) entity;
				painting.setArt(nextPosition(painting.getArt(), next, Art.values()));
				
				break;
			case AWAKE:
				if(!(entity instanceof Bat)) break;
				Bat bat = (Bat) entity;
				
				bat.setAwake(!bat.isAwake());
				
				break;
			case BODY_COLOR:
				if(!(entity instanceof TropicalFish)) break;
				TropicalFish tropicalFish = (TropicalFish) entity;
				tropicalFish.setBodyColor(nextPosition(tropicalFish.getBodyColor(), next, DyeColor.values()));
				
				break;
			case BOUNCE:
				if(!(entity instanceof Projectile)) break;
				Projectile projectile = (Projectile) entity;
				
				projectile.setBounce(!projectile.doesBounce());
				
				break;
			case CARRYING_CHEST:
				if(!(entity instanceof ChestedHorse)) break;
				ChestedHorse chestedHorse = (ChestedHorse) entity;
				chestedHorse.setCarryingChest(!chestedHorse.isCarryingChest());
					
				break;
			case CHANGE_AGE:
				if(!(entity instanceof Zombie)) break;
				Zombie zombie = (Zombie) entity;
				zombie.setBaby(!zombie.isBaby());
				
				break;
			case CHARGED:
				if(!(entity instanceof WitherSkull)) break;
				WitherSkull witherSkull = (WitherSkull) entity;
				
				witherSkull.setCharged(!witherSkull.isCharged());
				
				break;
			case CHARGING:
				if(!(entity instanceof Vex)) break;
				Vex vex = (Vex) entity;
				
				vex.setCharging(!vex.isCharging());
				
				break;
			case COLLAR_COLOR:
				if(entity instanceof Cat) {
					Cat cat = (Cat) entity;
					cat.setCollarColor(nextPosition(cat.getCollarColor(), next, DyeColor.values()));
				} else if(entity instanceof Wolf) {
					Wolf wolf = (Wolf) entity;
					wolf.setCollarColor(nextPosition(wolf.getCollarColor(), next, DyeColor.values()));
				}
				
				break;
			case COLOR:
				if(entity instanceof Horse) {
					Horse horse = (Horse) entity;
					horse.setColor(nextPosition(horse.getColor(), next, Horse.Color.values()));
				} else if(entity instanceof Llama) {
					Llama llama = (Llama) entity;
					llama.setColor(nextPosition(llama.getColor(), next, Llama.Color.values()));
				}
				
				break;
			case CONVERSION_TIME:
				if(entity instanceof Husk) {
					Husk husk = (Husk) entity;
					husk.setConversionTime(nextInt(husk.getConversionTime(), 64, next));
				} else if(entity instanceof PigZombie) {
					pigZombie = (PigZombie) entity;
					pigZombie.setConversionTime(nextInt(pigZombie.getConversionTime(), 64, next));
				} else if(entity instanceof Zombie) {
					zombie = (Zombie) entity;
					zombie.setConversionTime(nextInt(zombie.getConversionTime(), 64, next));
				}
				
				break;
			case CRITICAL:
				if(!(entity instanceof AbstractArrow)) break;
				AbstractArrow abstractArrow = (AbstractArrow) entity;
				abstractArrow.setCritical(!abstractArrow.isCritical());
				
				break;
			case CROUCHING:
				if(!(entity instanceof Fox)) break;
				Fox fox = (Fox) entity;
				fox.setCrouching(!fox.isCrouching());
				
				break;
			case DAMAGE:
				if(entity instanceof AbstractArrow) {
					abstractArrow = (AbstractArrow) entity;
					abstractArrow.setDamage(nextDouble(abstractArrow.getDamage(), 64, next));
				} else if(entity instanceof Minecart) {
					Minecart minecart = (Minecart) entity;
					minecart.setDamage(nextDouble(minecart.getDamage(), 64, next));
				}
				
				
				break;
			case DERP:
				if(!(entity instanceof Snowman)) break;
				Snowman snowman = (Snowman) entity;
				
				snowman.setDerp(!snowman.isDerp());
				
				break;
			case DESPAWN_TIMER:
				if(!(entity instanceof EnderSignal)) break;
				EnderSignal enderSignal = (EnderSignal) entity;
				enderSignal.setDespawnTimer(nextInt(enderSignal.getDespawnTimer(), 200, next));
				
				break;
			case DISPLAY_BLOCK_OFFSET:
				if(!(entity instanceof Minecart)) break;
				Minecart minecart = (Minecart) entity;
				minecart.setDisplayBlockOffset(nextInt(minecart.getDisplayBlockOffset(), 64, next));
				
				break;
			case DOMESTICATION:
				if(!(entity instanceof AbstractHorse)) break;
				AbstractHorse abstractHorse = (AbstractHorse) entity;
				abstractHorse.setDomestication(nextInt(abstractHorse.getDomestication(), 64, next));
				
				break;
			case DROP_ITEM:
				if(entity instanceof EnderSignal) {
					enderSignal = (EnderSignal) entity;
					enderSignal.setDropItem(!enderSignal.getDropItem());
				} else if(entity instanceof FallingBlock) {
					FallingBlock fallingBlock = (FallingBlock) entity;
					fallingBlock.setDropItem(!fallingBlock.getDropItem());
				}
				
				break;
			case EXPERIENCE:
				if(!(entity instanceof ExperienceOrb)) break;
				ExperienceOrb experienceOrb = (ExperienceOrb) entity;
				experienceOrb.setExperience(nextInt(experienceOrb.getExperience(), 64, next));
				
				break;
			case EXPLOSION_RADIUS:
				if(!(entity instanceof Creeper)) break;
				Creeper creeper = (Creeper) entity;
				creeper.setExplosionRadius(nextInt(creeper.getExplosionRadius(), 64, next));
				
				break;
			case FUSE_TICKS:
				if(!(entity instanceof TNTPrimed)) break;
				TNTPrimed tntPrimed = (TNTPrimed) entity;
				tntPrimed.setFuseTicks(nextInt(tntPrimed.getFuseTicks(), 64, next));
				
				break;
			case GLOWING_TICKS:
				if(!(entity instanceof SpectralArrow)) break;
				SpectralArrow spectralArrow = (SpectralArrow) entity;
				spectralArrow.setGlowingTicks(nextInt(spectralArrow.getGlowingTicks(), 200, next));
				
				break;
			case HIDDEN_GENE:
				if(!(entity instanceof Panda)) break;
				Panda panda = (Panda) entity;
				panda.setHiddenGene(nextPosition(panda.getHiddenGene(), next, Panda.Gene.values()));
				
				break;
			case HURT_ENTITIES:
				if(!(entity instanceof FallingBlock)) break;
				FallingBlock fallingBlock = (FallingBlock) entity;
				fallingBlock.setHurtEntities(!fallingBlock.canHurtEntities());
				
				break;
			case IS_INCENDIARY:
				if(!(entity instanceof Explosive)) break;
				Explosive explosive = (Explosive) entity;
				explosive.setIsIncendiary(!explosive.isIncendiary());
				
				break;
			case JUMP_STRENGTH:
				if(!(entity instanceof AbstractHorse)) break;
				abstractHorse = (AbstractHorse) entity;
				abstractHorse.setJumpStrength(nextDouble(abstractHorse.getJumpStrength(), 64, next));
				
				break;
			case MAIN_GENE:
				if(!(entity instanceof Panda)) break;
				panda = (Panda) entity;
				panda.setMainGene(nextPosition(panda.getMainGene(), next, Panda.Gene.values()));
				
				break;
			case MAX_FUSE_TICKS:
				if(!(entity instanceof Creeper)) break;
				creeper = (Creeper) entity;
				creeper.setMaxFuseTicks(nextInt(creeper.getMaxFuseTicks(), 64, next));
				
				break;
			case MAXSPEED:
				if(entity instanceof Minecart) {
					minecart = (Minecart) entity;
					minecart.setMaxSpeed(nextDouble(minecart.getMaxSpeed(), 200, next));
				} else if(entity instanceof Boat) {
					Boat boat = (Boat) entity;
					boat.setWorkOnLand(!boat.getWorkOnLand());
				}
				
				break;
			case OCCUPIED_DECELERATION:
				if(!(entity instanceof Boat)) break;
				Boat boat = (Boat) entity;
				boat.setOccupiedDeceleration(nextDouble(boat.getOccupiedDeceleration(), 64, next));
				
				break;
			case PATROL_LEADER:
				if(!(entity instanceof Raider)) break;
				Raider raider = (Raider) entity;
				
				raider.setPatrolLeader(!raider.isPatrolLeader());
				
				break;
			case PATTERN:
				if(!(entity instanceof TropicalFish)) break;
				tropicalFish = (TropicalFish) entity;
				tropicalFish.setPattern(nextPosition(tropicalFish.getPattern(), next, TropicalFish.Pattern.values()));
				
				break;
			case PATTERN_COLOR:
				if(!(entity instanceof TropicalFish)) break;
				tropicalFish = (TropicalFish) entity;
				tropicalFish.setPatternColor(nextPosition(tropicalFish.getPatternColor(), next, DyeColor.values()));
				
				break;
			case PICKUP_DELAY:
				if(!(entity instanceof Item)) break;
				Item item = (Item) entity;
				item.setPickupDelay(nextInt(item.getPickupDelay(), 200, next));
				
				break;
			case PICKUP_STATUS:
				if(!(entity instanceof AbstractArrow)) break;
				abstractArrow = (AbstractArrow) entity;
				abstractArrow.setPickupStatus(nextPosition(abstractArrow.getPickupStatus(), next, AbstractArrow.PickupStatus.values()));
				
				break;
			case PIERCE_LEVEL:
				if(!(entity instanceof AbstractArrow)) break;
				abstractArrow = (AbstractArrow) entity;
				abstractArrow.setPierceLevel(nextInt(abstractArrow.getPierceLevel(), 64, next));
				
				break;
			case PLAYER_CREATED:
				if(!(entity instanceof IronGolem)) break;
				IronGolem ironGolem = (IronGolem) entity;
				
				ironGolem.setPlayerCreated(!ironGolem.isPlayerCreated());
				
				break;
			case POWERED:
				if(!(entity instanceof Creeper)) break;
				creeper = (Creeper) entity;
				creeper.setPowered(!creeper.isPowered());
				
				break;
			case PROFESSION:
				if(!(entity instanceof Villager)) break;
				Villager villager = (Villager) entity;
				villager.setProfession(nextPosition(villager.getProfession(), next, Villager.Profession.values()));
				
				break;
			case PUFF_STATE:
				if(!(entity instanceof PufferFish)) break;
				PufferFish pufferFish = (PufferFish) entity;
				pufferFish.setPuffState(nextInt(pufferFish.getPuffState(), 2, next));
				
				break;
			case SADDLE:
				if(!(entity instanceof Pig)) break;
				Pig pig = (Pig) entity;
				
				pig.setSaddle(!pig.hasSaddle());
				
				break;
			case SHEARED:
				if(!(entity instanceof Sheep)) break;
				Sheep sheep = (Sheep) entity;
				
				sheep.setSheared(!sheep.isSheared());
				
				break;
			case SHOT_AT_ANGLE:
				if(!(entity instanceof Firework)) break;
				Firework firework = (Firework) entity;
				
				firework.setShotAtAngle(!firework.isShotAtAngle());
				
				break;
			case SHOWING_BOTTOM:
				if(!(entity instanceof EnderCrystal)) break;
				EnderCrystal enderCrystal = (EnderCrystal) entity;
				
				enderCrystal.setShowingBottom(!enderCrystal.isShowingBottom());
				
				break;
			case SIZE:
				if(entity instanceof Phantom) {
					Phantom phantom = (Phantom) entity;
					phantom.setSize(nextInt(phantom.getSize(), 30, next));
				} else if(entity instanceof Slime) {
					Slime slime = (Slime) entity;
					slime.setSize(nextInt(slime.getSize(), 30, next));
				}
				
				break;
			case SLEEPING:
				if(entity instanceof Fox) {
					fox = (Fox) entity;
					fox.setSleeping(!fox.isSleeping());
				} else if(entity instanceof Villager) {
					villager = (Villager) entity;
					
					if(villager.isSleeping())
						villager.wakeup();
					else
						villager.sleep(villager.getLocation());
				}

				break;
			case SLOW_WHEN_EMPTY:
				if(!(entity instanceof Minecart)) break;
				minecart = (Minecart) entity;
				minecart.setSlowWhenEmpty(!minecart.isSlowWhenEmpty());
				
				break;
			case SPELL:
				if(!(entity instanceof Spellcaster)) break;
				Spellcaster spellcaster = (Spellcaster) entity;
				spellcaster.setSpell(nextPosition(spellcaster.getSpell(), next, Spellcaster.Spell.values()));
				
				break;
			case STRENGTH:
				if(!(entity instanceof Llama)) break;
				Llama llama = (Llama) entity;
				llama.setStrength(nextInt(llama.getStrength(), 20, next));
				
				break;
			case STYLE:
				if(!(entity instanceof Horse)) break;
				Horse horse = (Horse) entity;
				horse.setStyle(nextPosition(horse.getStyle(), next, Horse.Style.values()));
				
				break;
			case TYPE:
				if(entity instanceof Boat) {
					boat = (Boat) entity;
					boat.setWoodType(nextPosition(boat.getWoodType(), next, TreeSpecies.values()));
				} else if(entity instanceof Cat) {
					Cat cat = (Cat) entity;
					cat.setCatType(nextPosition(cat.getCatType(), next, Cat.Type.values()));
				} else if(entity instanceof Fox) {
					fox = (Fox) entity;
					fox.setFoxType(nextPosition(fox.getFoxType(), next, Fox.Type.values()));
				} else if(entity instanceof Rabbit) {
					Rabbit rabbit = (Rabbit) entity;
					rabbit.setRabbitType(nextPosition(rabbit.getRabbitType(), next, Rabbit.Type.values()));
				} else if(entity instanceof Villager) {
					villager = (Villager) entity;
					villager.setVillagerType(nextPosition(villager.getVillagerType(), next, Villager.Type.values()));
				}
				
				break;
			case VARIANT:
				if(entity instanceof MushroomCow) {
					MushroomCow mushroomCow = (MushroomCow) entity;
					mushroomCow.setVariant(nextPosition(mushroomCow.getVariant(), next, MushroomCow.Variant.values()));
				} else if(entity instanceof Parrot) {
					Parrot parrot = (Parrot) entity;
					parrot.setVariant(nextPosition(parrot.getVariant(), next, Parrot.Variant.values()));
				}
				break;
			case VILLAGER_EXPERIENCE:
				if(!(entity instanceof Villager)) break;
				villager = (Villager) entity;
				villager.setVillagerExperience(nextInt(villager.getVillagerExperience(), 64, next));
				
				break;
			case VILLAGER_LEVEL:
				if(!(entity instanceof Villager)) break;
				villager = (Villager) entity;
				villager.setVillagerLevel(nextInt(villager.getVillagerLevel(), 64, next));
				
				break;
			case WORK_ON_LAND:
				if(!(entity instanceof Boat)) break;
				boat = (Boat) entity;
				boat.setWorkOnLand(!boat.getWorkOnLand());
				
				break;
			case YIELD:
				if(!(entity instanceof Explosive)) break;
				explosive = (Explosive) entity;
				explosive.setYield((float) nextDouble(explosive.getYield(), 64, next));
				break;
			default:
				break;
		}
	}
	
	@SafeVarargs
	private static <T extends Enum<T>> T nextPosition(T currentEnum, boolean next, T... enums) {
		int count = 0;
		for(T enumValue : enums) {
			Bukkit.broadcastMessage(enumValue + " " + currentEnum + " " + enumValue.equals(currentEnum));
			if(enumValue.equals(currentEnum))
				break;
			count++;
		}
		return enums[nextInt(count, enums.length - 1, next)];
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
