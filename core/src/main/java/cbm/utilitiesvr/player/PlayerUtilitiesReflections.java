package cbm.utilitiesvr.player;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import cbm.main.Main;
import cbm.utilitiesvr.itemstack.ItemStackUtilitiesReflections;
import cbm.versions.minecraft.ReflectionsUtilities;
import components.reflection.ConstructorReflection;
import components.reflection.MethodReflection;
import components.reflection.ObjectReflection;

public class PlayerUtilitiesReflections {
	public static void sendPacket(Player player, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchFieldException {
		Object playerConnection = getPlayerConnection(player);
		MethodReflection.callMethod(playerConnection, "sendPacket", obj);
	}

	public static Object getPlayerConnection(Player player) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchFieldException {
		return ObjectReflection.getObject("playerConnection", getEntityPlayer(player));
	}

	public static Object getEntityPlayerFromPlayerConnection(Player player) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchFieldException {
		return ObjectReflection.getObject("player", getPlayerConnection(player));
	}

	public static Object getEntityPlayer(Player player) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return MethodReflection.callMethod(player, "getHandle");
	}
	
	public static Object getPacket(String packetName, Object... args) {
		try {
			return ConstructorReflection.createObject(ReflectionsUtilities.getMCClass(packetName), args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setArmSwing(Player player, EnumHandUtil hand) {
		try {
			switch (hand) {
				case MAIN_HAND:
					setArmSwing(player, ObjectReflection.getEnum((Class<Enum>) Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".EnumHand"), "MAIN_HAND"));
					break;
				case OFF_HAND:
					setArmSwing(player, ObjectReflection.getEnum((Class<Enum>) Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".EnumHand"), "OFF_HAND"));
					break;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void setArmSwing(Player player, Object hand) {
		try {
			sendPacket(player, getPacket("PacketPlayInArmAnimation", hand));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public static void setHeldItemSlot(Player player, int number) {
		try {
			sendPacket(player, getPacket("PacketPlayOutHeldItemSlot", number));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public static void setGameProfile(Player player, GameProfile gameProfile) {
		try {
			Field field = ObjectReflection.getField("bW", Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".EntityHuman"));
			field.setAccessible(true);
			field.set(getEntityPlayer(player), gameProfile);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static GameProfile getGameProfile(Player player) {
		try {
			return (GameProfile) MethodReflection.callMethod(getEntityPlayer(player), "getProfile");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void updatePlayer(final Player player) {
		try {
			Object entityPlayer = getEntityPlayer(player);
	
			boolean flying = player.isFlying();
			int entityID = player.getEntityId();
	
			Object packetRemovePlayer = getPacket("PacketPlayOutPlayerInfo", getEnumPlayerInfoAction("REMOVE_PLAYER"), entityPlayer);
			
			Object entityPlayers = Array.newInstance(entityPlayer.getClass(), 1);
			Array.set(entityPlayers, 0, entityPlayer);
			Object packetAddPlayer = getPacket("PacketPlayOutPlayerInfo", getEnumPlayerInfoAction("ADD_PLAYER"), entityPlayers);
			
			Object packetPlayOutEntityDestroy = getPacket("PacketPlayOutEntityDestroy", entityID);
			Object packetPlayOutNamedEntitySpawn = getPacket("PacketPlayOutNamedEntitySpawn", entityPlayer);
	
			final Object headItemPacket = getPacket("PacketPlayOutEntityEquipment", entityID, getEnumItemSlot("HEAD"), ItemStackUtilitiesReflections.craftItemStackAsNMSCopy(player.getInventory().getHelmet()));
			final Object chestItemPacket = getPacket("PacketPlayOutEntityEquipment", entityID, getEnumItemSlot("CHEST"), ItemStackUtilitiesReflections.craftItemStackAsNMSCopy(player.getInventory().getChestplate()));
			final Object legsItemPacket = getPacket("PacketPlayOutEntityEquipment", entityID, getEnumItemSlot("LEGS"), ItemStackUtilitiesReflections.craftItemStackAsNMSCopy(player.getInventory().getLeggings()));
			final Object feetItemPacket = getPacket("PacketPlayOutEntityEquipment", entityID, getEnumItemSlot("FEET"), ItemStackUtilitiesReflections.craftItemStackAsNMSCopy(player.getInventory().getBoots()));
			final Object handItemPacket = getPacket("PacketPlayOutEntityEquipment", entityID, getEnumItemSlot("MAINHAND"), ItemStackUtilitiesReflections.craftItemStackAsNMSCopy(player.getInventory().getItemInMainHand()));
			final Object offHandItemPacket = getPacket("PacketPlayOutEntityEquipment", entityID, getEnumItemSlot("OFFHAND"), ItemStackUtilitiesReflections.craftItemStackAsNMSCopy(player.getInventory().getItemInOffHand()));
			
			Object dimensionManager;
			
			switch (player.getWorld().getEnvironment()) {
				case NETHER:
					dimensionManager = ObjectReflection.getField("NETHER", Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".DimensionManager")).get(null);
					break;
				case THE_END:
					dimensionManager = ObjectReflection.getField("THE_END", Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".DimensionManager")).get(null);
					break;
					
				case NORMAL:
				default:
					dimensionManager = ObjectReflection.getField("OVERWORLD", Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".DimensionManager")).get(null);
			}
	
			for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				if (onlinePlayer == player) {
					sendPacket(onlinePlayer, packetRemovePlayer);
					sendPacket(onlinePlayer, packetAddPlayer);
					Location location = player.getLocation();
	
					Object packetPlayOutPosition = getPacket("PacketPlayOutPosition", location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), new HashSet<>(), 0);
					
					Object world = MethodReflection.callMethod(entityPlayer, "getWorld");
					Object worldType = MethodReflection.callMethod(ObjectReflection.getField("worldData", world).get(world), "getType");
					Object packetPlayOutRespawn = getPacket("PacketPlayOutRespawn", dimensionManager, worldType, getEnumGamemode(player.getGameMode().name()));
	
					sendPacket(onlinePlayer, packetPlayOutRespawn);
					sendPacket(onlinePlayer, packetPlayOutPosition);
	
					for (Object mobEffect : getEffects(player))
						sendPacket(onlinePlayer, getPacket("PacketPlayOutEntityEffect", entityID, mobEffect));
	
					Object packetPlayOutUpdateHealth = getPacket("PacketPlayOutUpdateHealth", (float) player.getHealth(), player.getFoodLevel(), player.getSaturation());
					sendPacket(onlinePlayer, packetPlayOutUpdateHealth);
					
					updateEXP(player);
					player.updateInventory();
					
					Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
						if (flying)
							Bukkit.getPlayer(player.getName()).setFlying(true);
					}, 1l);
					
					continue;
				}
				
				if (!onlinePlayer.canSee(player))
					continue;
				
				sendPacket(onlinePlayer, packetRemovePlayer);
				sendPacket(onlinePlayer, packetAddPlayer);
				sendPacket(onlinePlayer, packetPlayOutEntityDestroy);
				sendPacket(onlinePlayer, packetPlayOutNamedEntitySpawn);
	
				sendPacket(onlinePlayer, headItemPacket);
				sendPacket(onlinePlayer, chestItemPacket);
				sendPacket(onlinePlayer, legsItemPacket);
				sendPacket(onlinePlayer, feetItemPacket);
				sendPacket(onlinePlayer, handItemPacket);
				sendPacket(onlinePlayer, offHandItemPacket);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected static Object getEnumItemSlot(String name) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> classy = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".EnumItemSlot");
		return MethodReflection.callStaticMethod(classy, "valueOf", name);
	}
	
	protected static Object getEnumGamemode(String name) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> classy = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".EnumGamemode");
		return MethodReflection.callStaticMethod(classy, "valueOf", name);
	}
	
	protected static Object getEnumPlayerInfoAction(String name) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> classy = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
		return MethodReflection.callStaticMethod(classy, "valueOf", name);
	}
	
	protected static Collection<?> getEffects(Player player) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return (Collection<?>) MethodReflection.callMethod(getEntityPlayer(player), "getEffects");
	}
	
	public static void updateEXP(Player player) {
		try {
			Object entityPlayer = getEntityPlayer(player);
			Field field = ObjectReflection.getField("lastSentExp", entityPlayer);
			field.setInt(entityPlayer, -1);
		} catch (IllegalAccessException | IllegalArgumentException | SecurityException | NoSuchFieldException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
