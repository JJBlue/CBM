package cbm.v1_16_R1.utilitiesvr.player;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import cbm.utilitiesvr.player.EnumHandUtil;
import cbm.utilitiesvr.player.PlayerUtilities_Interface;
import components.reflection.ObjectReflection;
import net.minecraft.server.v1_16_R1.EntityHuman;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import net.minecraft.server.v1_16_R1.EnumGamemode;
import net.minecraft.server.v1_16_R1.EnumHand;
import net.minecraft.server.v1_16_R1.MobEffect;
import net.minecraft.server.v1_16_R1.Packet;
import net.minecraft.server.v1_16_R1.PacketPlayInArmAnimation;
import net.minecraft.server.v1_16_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R1.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_16_R1.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_16_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R1.PacketPlayOutPosition;
import net.minecraft.server.v1_16_R1.PacketPlayOutRespawn;
import net.minecraft.server.v1_16_R1.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_16_R1.PlayerConnection;

public class PlayerUtilities_Impl implements PlayerUtilities_Interface {
	@Override
	public void sendPacket(Player player, Object packet) {
		sendPacket(player, (Packet<?>) packet);
	}
	
	public static void sendPacket(Player player, Packet<?> packet) {
		getPlayerConnection(player).sendPacket(packet);
	}

	public static EntityPlayer getEntityPlayerFromPlayerConnection(Player player) {
		return getPlayerConnection(player).player;
	}

	public static PlayerConnection getPlayerConnection(Player player) {
		return getEntityPlayer(player).playerConnection;
	}

	public static EntityPlayer getEntityPlayer(Player player) {
		return ((CraftPlayer) player).getHandle();
	}
	
	@Override
	public void setArmSwing(Player player, EnumHandUtil hand) {
		switch (hand) {
			case MAIN_HAND:
				setArmSwing(player, EnumHand.MAIN_HAND);
				break;
			case OFF_HAND:
				setArmSwing(player, EnumHand.OFF_HAND);
				break;
		}
	}
	
	public void setArmSwing(Player player, EnumHand hand) {
		((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInArmAnimation(hand));
	}
	
	@Override
	public void setHeldItemSlot(Player player, int number) {
		PacketPlayOutHeldItemSlot animation = new PacketPlayOutHeldItemSlot(number);
		sendPacket(player, animation);
	}
	
	@Override
	public void setGameProfile(Player player, GameProfile gameProfile) {
		try {
			Field field = ObjectReflection.getField("bQ", EntityHuman.class);
			field.setAccessible(true);
			field.set(((CraftPlayer) player).getHandle(), gameProfile);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public GameProfile getGameProfile(Player player) {
		return ((CraftPlayer) player).getHandle().getProfile();
	}
	
	@Override
	public void updatePlayer(final Player player) {
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

		boolean flying = player.isFlying();
		int entityID = player.getEntityId();
		int heldItem = player.getInventory().getHeldItemSlot();

		PacketPlayOutPlayerInfo packetRemovePlayer = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer);
		PacketPlayOutPlayerInfo packetAddPlayer = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
		
		PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityID);
		PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(entityPlayer);
		
//		final PacketPlayOutEntityEquipment headItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(player.getInventory().getHelmet()));
//		final PacketPlayOutEntityEquipment chestItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(player.getInventory().getChestplate()));
//		final PacketPlayOutEntityEquipment legsItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(player.getInventory().getLeggings()));
//		final PacketPlayOutEntityEquipment feetItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.FEET, CraftItemStack.asNMSCopy(player.getInventory().getBoots()));
//		final PacketPlayOutEntityEquipment handItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(player.getInventory().getItemInMainHand()));
//		final PacketPlayOutEntityEquipment offHandItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(player.getInventory().getItemInOffHand()));

		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			final PlayerConnection playerConnection = (((CraftPlayer) onlinePlayer).getHandle()).playerConnection;
			
			if (onlinePlayer == player) {
				playerConnection.sendPacket(packetRemovePlayer);
				playerConnection.sendPacket(packetAddPlayer);
				Location location = player.getLocation();

				PacketPlayOutPosition packetPlayOutPosition = new PacketPlayOutPosition(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), new HashSet<>(), 0);
				PacketPlayOutRespawn packetPlayOutRespawn = new PacketPlayOutRespawn(
					entityPlayer.getWorld().getTypeKey(),
					entityPlayer.getWorld().getDimensionKey(),
					0, // First 8 bytes of the SHA-256 hash of the world's seed.
					EnumGamemode.valueOf(player.getGameMode().name()),
					EnumGamemode.valueOf(player.getGameMode().name()),
					entityPlayer.getWorld().isDebugWorld(),
					false, // flat world?
					false // normal respawn? or changed dimension
				);
				
				playerConnection.sendPacket(packetPlayOutRespawn);
				playerConnection.sendPacket(packetPlayOutPosition);

				for (MobEffect mobEffect : entityPlayer.getEffects())
					playerConnection.sendPacket(new PacketPlayOutEntityEffect(entityID, mobEffect));

				PacketPlayOutUpdateHealth packetPlayOutUpdateHealth = new PacketPlayOutUpdateHealth((float) player.getHealth(), player.getFoodLevel(), player.getSaturation());
				playerConnection.sendPacket(packetPlayOutUpdateHealth);
				
				setHeldItemSlot(player, heldItem);
				
				entityPlayer.lastSentExp = -1;
				player.updateInventory();
				
				if (flying)
					Bukkit.getPlayer(player.getName()).setFlying(true);
				
				continue;
			}
			
			if (!onlinePlayer.canSee(player))
				continue;
			
			playerConnection.sendPacket(packetRemovePlayer);
			playerConnection.sendPacket(packetAddPlayer);
			playerConnection.sendPacket(packetPlayOutEntityDestroy);
			playerConnection.sendPacket(packetPlayOutNamedEntitySpawn);

//			playerConnection.sendPacket(headItemPacket);
//			playerConnection.sendPacket(chestItemPacket);
//			playerConnection.sendPacket(legsItemPacket);
//			playerConnection.sendPacket(feetItemPacket);
//			playerConnection.sendPacket(handItemPacket);
//			playerConnection.sendPacket(offHandItemPacket);
		}
	}
}
