package essentials.modules.debugstick.entity;

import org.bukkit.entity.*;

public class DebugStickEntityGetValue {
	@SuppressWarnings("deprecation")
	public static Object getEntityStateValue(Entity entity, DebugStickEntityChanges type) {
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
		
		if(entity != null) {

			switch(type) {
				case CUSTOM_NAME_VISIBLE:
					return entity.isCustomNameVisible();
				case FIRE_TRICKS:
					return entity.getFireTicks();
				case GLOWING:
					return entity.isGlowing();
				case GRAVITY:
					return entity.hasGravity();
				case INVULNERABLE:
					return entity.isInvulnerable();
				case PERSISTENT:
					return entity.isPersistent();
				case SILENT:
					return entity.isSilent();
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
		
		//############################################################################
		switch (type) {
			case ANGER:
				if(!(entity instanceof PigZombie)) break;
				PigZombie pigZombie = (PigZombie) entity;
				return pigZombie.getAnger();
				
			case ANGRY:
				if(entity instanceof PigZombie) {
					pigZombie = (PigZombie) entity;
					return pigZombie.isAngry();
				} else if(entity instanceof Wolf) {
					Wolf wolf = (Wolf) entity;
					return wolf.isAngry();
				}
				
				break;
			case ART:
				if(!(entity instanceof Painting)) break;
				Painting painting = (Painting) entity;
				return painting.getArt();
				
			case AWAKE:
				if(!(entity instanceof Bat)) break;
				Bat bat = (Bat) entity;
				return bat.isAwake();
				
			case BODY_COLOR:
				if(!(entity instanceof TropicalFish)) break;
				TropicalFish tropicalFish = (TropicalFish) entity;
				return tropicalFish.getBodyColor();
				
			case BOUNCE:
				if(!(entity instanceof Projectile)) break;
				Projectile projectile = (Projectile) entity;
				return projectile.doesBounce();
				
			case CARRYING_CHEST:
				if(!(entity instanceof ChestedHorse)) break;
				ChestedHorse chestedHorse = (ChestedHorse) entity;
				return chestedHorse.isCarryingChest();
					
			case CHANGE_AGE:
				if(!(entity instanceof Zombie)) break;
				Zombie zombie = (Zombie) entity;
				return zombie.isBaby();
				
			case CHARGED:
				if(!(entity instanceof WitherSkull)) break;
				WitherSkull witherSkull = (WitherSkull) entity;
				return witherSkull.isCharged();
				
			case CHARGING:
				if(!(entity instanceof Vex)) break;
				Vex vex = (Vex) entity;
				return vex.isCharging();
				
			case COLLAR_COLOR:
				if(entity instanceof Cat) {
					Cat cat = (Cat) entity;
					return cat.getCollarColor();
				} else if(entity instanceof Wolf) {
					Wolf wolf = (Wolf) entity;
					return wolf.getCollarColor();
				}
				
				break;
			case COLOR:
				if(entity instanceof Horse) {
					Horse horse = (Horse) entity;
					return horse.getColor();
				} else if(entity instanceof Llama) {
					Llama llama = (Llama) entity;
					return llama.getColor();
				}
				
				break;
			case CONVERSION_TIME:
				if(entity instanceof Husk) {
					Husk husk = (Husk) entity;
					if(!husk.isConverting()) return -1;
					return husk.getConversionTime();
				} else if(entity instanceof PigZombie) {
					pigZombie = (PigZombie) entity;
					if(!pigZombie.isConverting()) return -1;
					return pigZombie.getConversionTime();
				} else if(entity instanceof Zombie) {
					zombie = (Zombie) entity;
					if(!zombie.isConverting()) return -1;
					return zombie.getConversionTime();
				}
				
				break;
			case CRITICAL:
				if(!(entity instanceof AbstractArrow)) break;
				AbstractArrow abstractArrow = (AbstractArrow) entity;
				return abstractArrow.isCritical();
				
			case CROUCHING:
				if(!(entity instanceof Fox)) break;
				Fox fox = (Fox) entity;
				return fox.isCrouching();
				
			case DAMAGE:
				if(entity instanceof AbstractArrow) {
					abstractArrow = (AbstractArrow) entity;
					return abstractArrow.getDamage();
				} else if(entity instanceof Minecart) {
					Minecart minecart = (Minecart) entity;
					return minecart.getDamage();
				}
				
				break;
			case DERP:
				if(!(entity instanceof Snowman)) break;
				Snowman snowman = (Snowman) entity;
				return snowman.isDerp();
				
			case DESPAWN_TIMER:
				if(!(entity instanceof EnderSignal)) break;
				EnderSignal enderSignal = (EnderSignal) entity;
				return enderSignal.getDespawnTimer();
				
			case DISPLAY_BLOCK_OFFSET:
				if(!(entity instanceof Minecart)) break;
				Minecart minecart = (Minecart) entity;
				return minecart.getDisplayBlockOffset();
				
			case DOMESTICATION:
				if(!(entity instanceof AbstractHorse)) break;
				AbstractHorse abstractHorse = (AbstractHorse) entity;
				return abstractHorse.getDomestication();
				
			case DROP_ITEM:
				if(entity instanceof EnderSignal) {
					enderSignal = (EnderSignal) entity;
					return enderSignal.getDropItem();
				} else if(entity instanceof FallingBlock) {
					FallingBlock fallingBlock = (FallingBlock) entity;
					return fallingBlock.getDropItem();
				}
				
				break;
			case EXPERIENCE:
				if(!(entity instanceof ExperienceOrb)) break;
				ExperienceOrb experienceOrb = (ExperienceOrb) entity;
				return experienceOrb.getExperience();
				
			case EXPLOSION_RADIUS:
				if(!(entity instanceof Creeper)) break;
				Creeper creeper = (Creeper) entity;
				return creeper.getExplosionRadius();
				
			case FUSE_TICKS:
				if(!(entity instanceof TNTPrimed)) break;
				TNTPrimed tntPrimed = (TNTPrimed) entity;
				return tntPrimed.getFuseTicks();
				
			case GLOWING_TICKS:
				if(!(entity instanceof SpectralArrow)) break;
				SpectralArrow spectralArrow = (SpectralArrow) entity;
				return spectralArrow.getGlowingTicks();
				
			case HIDDEN_GENE:
				if(!(entity instanceof Panda)) break;
				Panda panda = (Panda) entity;
				return panda.getHiddenGene();
				
			case HURT_ENTITIES:
				if(!(entity instanceof FallingBlock)) break;
				FallingBlock fallingBlock = (FallingBlock) entity;
				return fallingBlock.canHurtEntities();
				
			case IS_INCENDIARY:
				if(!(entity instanceof Explosive)) break;
				Explosive explosive = (Explosive) entity;
				return explosive.isIncendiary();
				
			case JUMP_STRENGTH:
				if(!(entity instanceof AbstractHorse)) break;
				abstractHorse = (AbstractHorse) entity;
				return abstractHorse.getJumpStrength();
				
			case MAIN_GENE:
				if(!(entity instanceof Panda)) break;
				panda = (Panda) entity;
				return panda.getMainGene();
				
			case MAX_FUSE_TICKS:
				if(!(entity instanceof Creeper)) break;
				creeper = (Creeper) entity;
				return creeper.getMaxFuseTicks();
				
			case MAXSPEED:
				if(entity instanceof Minecart) {
					minecart = (Minecart) entity;
					return minecart.getMaxSpeed();
				} else if(entity instanceof Boat) {
					Boat boat = (Boat) entity;
					return boat.getMaxSpeed();
				}
				
				break;
			case OCCUPIED_DECELERATION:
				if(!(entity instanceof Boat)) break;
				Boat boat = (Boat) entity;
				return boat.getOccupiedDeceleration();
				
			case PATROL_LEADER:
				if(!(entity instanceof Raider)) break;
				Raider raider = (Raider) entity;
				return raider.isPatrolLeader();
				
			case PATTERN:
				if(!(entity instanceof TropicalFish)) break;
				tropicalFish = (TropicalFish) entity;
				return tropicalFish.getPattern();
				
			case PATTERN_COLOR:
				if(!(entity instanceof TropicalFish)) break;
				tropicalFish = (TropicalFish) entity;
				return tropicalFish.getPatternColor();
				
			case PICKUP_DELAY:
				if(!(entity instanceof Item)) break;
				Item item = (Item) entity;
				return item.getPickupDelay();
				
			case PICKUP_STATUS:
				if(!(entity instanceof AbstractArrow)) break;
				abstractArrow = (AbstractArrow) entity;
				return abstractArrow.getPickupStatus();
				
			case PIERCE_LEVEL:
				if(!(entity instanceof AbstractArrow)) break;
				abstractArrow = (AbstractArrow) entity;
				return abstractArrow.getPierceLevel();
				
			case PLAYER_CREATED:
				if(!(entity instanceof IronGolem)) break;
				IronGolem ironGolem = (IronGolem) entity;
				return ironGolem.isPlayerCreated();
				
			case POWERED:
				if(!(entity instanceof Creeper)) break;
				creeper = (Creeper) entity;
				return creeper.isPowered();
				
			case PROFESSION:
				if(!(entity instanceof Villager)) break;
				Villager villager = (Villager) entity;
				return villager.getProfession();
				
			case PUFF_STATE:
				if(!(entity instanceof PufferFish)) break;
				PufferFish pufferFish = (PufferFish) entity;
				return pufferFish.getPuffState();
				
			case SADDLE:
				if(!(entity instanceof Pig)) break;
				Pig pig = (Pig) entity;
				return pig.hasSaddle();
				
			case SHEARED:
				if(!(entity instanceof Sheep)) break;
				Sheep sheep = (Sheep) entity;
				return sheep.isSheared();
				
			case SHOT_AT_ANGLE:
				if(!(entity instanceof Firework)) break;
				Firework firework = (Firework) entity;
				return firework.isShotAtAngle();
				
			case SHOWING_BOTTOM:
				if(!(entity instanceof EnderCrystal)) break;
				EnderCrystal enderCrystal = (EnderCrystal) entity;
				return enderCrystal.isShowingBottom();
				
			case SIZE:
				if(entity instanceof Phantom) {
					Phantom phantom = (Phantom) entity;
					return phantom.getSize();
				} else if(entity instanceof Slime) {
					Slime slime = (Slime) entity;
					return slime.getSize();
				}
				
				break;
			case SLEEPING:
				if(entity instanceof Fox) {
					fox = (Fox) entity;
					return fox.isSleeping();
				} else if(entity instanceof Villager) {
					villager = (Villager) entity;
					return villager.isSleeping();
				}
	
				break;
			case SLOW_WHEN_EMPTY:
				if(!(entity instanceof Minecart)) break;
				minecart = (Minecart) entity;
				return minecart.isSlowWhenEmpty();
				
			case SPELL:
				if(!(entity instanceof Spellcaster)) break;
				Spellcaster spellcaster = (Spellcaster) entity;
				return spellcaster.getSpell();
				
			case STRENGTH:
				if(!(entity instanceof Llama)) break;
				Llama llama = (Llama) entity;
				return llama.getStrength();
				
			case STYLE:
				if(!(entity instanceof Horse)) break;
				Horse horse = (Horse) entity;
				return horse.getStyle();
				
			case TYPE:
				if(entity instanceof Boat) {
					boat = (Boat) entity;
					return boat.getWoodType();
				} else if(entity instanceof Cat) {
					Cat cat = (Cat) entity;
					return cat.getCatType();
				} else if(entity instanceof Fox) {
					fox = (Fox) entity;
					return fox.getFoxType();
				} else if(entity instanceof Rabbit) {
					Rabbit rabbit = (Rabbit) entity;
					return rabbit.getRabbitType();
				} else if(entity instanceof Villager) {
					villager = (Villager) entity;
					return villager.getVillagerType();
				}
				
				break;
			case VARIANT:
				if(entity instanceof MushroomCow) {
					MushroomCow mushroomCow = (MushroomCow) entity;
					return mushroomCow.getVariant();
				} else if(entity instanceof Parrot) {
					Parrot parrot = (Parrot) entity;
					return parrot.getVariant();
				}
				break;
			case VILLAGER_EXPERIENCE:
				if(!(entity instanceof Villager)) break;
				villager = (Villager) entity;
				return villager.getVillagerExperience();
				
			case VILLAGER_LEVEL:
				if(!(entity instanceof Villager)) break;
				villager = (Villager) entity;
				return villager.getVillagerLevel();
				
			case WORK_ON_LAND:
				if(!(entity instanceof Boat)) break;
				boat = (Boat) entity;
				return boat.getWorkOnLand();
				
			case YIELD:
				if(!(entity instanceof Explosive)) break;
				explosive = (Explosive) entity;
				return explosive.getYield();
				
			default:
				break;
		}
		
		return null;
	}
}
