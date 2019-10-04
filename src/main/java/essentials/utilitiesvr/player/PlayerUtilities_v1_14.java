package essentials.utilitiesvr.player;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_14_R1.DimensionManager;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.EnumGamemode;
import net.minecraft.server.v1_14_R1.EnumItemSlot;
import net.minecraft.server.v1_14_R1.MobEffect;
import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_14_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_14_R1.PacketPlayOutPosition;
import net.minecraft.server.v1_14_R1.PacketPlayOutRespawn;
import net.minecraft.server.v1_14_R1.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_14_R1.PlayerConnection;

public class PlayerUtilities_v1_14 {
	public static void sendPacket(Player player, Object packet) {
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
	
	public static void updatePlayer(final Player player) {
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

		boolean flying = player.isFlying();
		int entityID = player.getEntityId();

		PacketPlayOutPlayerInfo packetRemovePlayer = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer);
		PacketPlayOutPlayerInfo packetAddPlayer = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
		
		PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityID);
		PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(entityPlayer);

		final PacketPlayOutEntityEquipment headItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(player.getInventory().getHelmet()));
		final PacketPlayOutEntityEquipment chestItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(player.getInventory().getChestplate()));
		final PacketPlayOutEntityEquipment legsItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(player.getInventory().getLeggings()));
		final PacketPlayOutEntityEquipment feetItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.FEET, CraftItemStack.asNMSCopy(player.getInventory().getBoots()));
		final PacketPlayOutEntityEquipment handItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(player.getInventory().getItemInMainHand()));
		final PacketPlayOutEntityEquipment offHandItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(player.getInventory().getItemInOffHand()));
		
		DimensionManager dimensionManager = DimensionManager.OVERWORLD;
		
		switch (player.getWorld().getEnvironment()) {
			case NETHER:
				dimensionManager = DimensionManager.NETHER;
				break;
			case NORMAL:
				dimensionManager = DimensionManager.OVERWORLD;
				break;
			case THE_END:
				dimensionManager = DimensionManager.THE_END;
				break;
		}

		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			final PlayerConnection playerConnection = (((CraftPlayer) onlinePlayer).getHandle()).playerConnection;
			
			if (onlinePlayer == player) {
				playerConnection.sendPacket(packetRemovePlayer);
				playerConnection.sendPacket(packetAddPlayer);
				Location location = player.getLocation();

				PacketPlayOutPosition packetPlayOutPosition = new PacketPlayOutPosition(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), new HashSet<>(), 0);
				PacketPlayOutRespawn packetPlayOutRespawn = new PacketPlayOutRespawn(dimensionManager, entityPlayer.getWorld().worldData.getType(), EnumGamemode.valueOf(player.getGameMode().name()));

				playerConnection.sendPacket(packetPlayOutRespawn);
				playerConnection.sendPacket(packetPlayOutPosition);

				for (MobEffect mobEffect : entityPlayer.getEffects())
					playerConnection.sendPacket(new PacketPlayOutEntityEffect(entityID, mobEffect));

				PacketPlayOutUpdateHealth packetPlayOutUpdateHealth = new PacketPlayOutUpdateHealth((float) player.getHealth(), player.getFoodLevel(), player.getSaturation());
				playerConnection.sendPacket(packetPlayOutUpdateHealth);
				
				if (flying)
					player.setFlying(true);
				
				entityPlayer.lastSentExp = -1;
				player.updateInventory();
				
				continue;
			}
			
			if (!onlinePlayer.canSee(player))
				continue;
			
			playerConnection.sendPacket(packetRemovePlayer);
			playerConnection.sendPacket(packetAddPlayer);
			playerConnection.sendPacket(packetPlayOutEntityDestroy);
			playerConnection.sendPacket(packetPlayOutNamedEntitySpawn);

			playerConnection.sendPacket(headItemPacket);
			playerConnection.sendPacket(chestItemPacket);
			playerConnection.sendPacket(legsItemPacket);
			playerConnection.sendPacket(feetItemPacket);
			playerConnection.sendPacket(handItemPacket);
			playerConnection.sendPacket(offHandItemPacket);
		}
	}
}
