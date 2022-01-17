package cbm.v1_18_R1.utilitiesvr.player;

import java.lang.reflect.Field;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import cbm.utilitiesvr.player.EnumHandUtil;
import cbm.utilitiesvr.player.PlayerUtilities_Interface;
import components.reflection.ObjectReflection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayInArmAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateHealth;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public class PlayerUtilities_Impl implements PlayerUtilities_Interface {
	@Override
	public void sendPacket(Player player, Object packet) {
		sendPacket(player, (Packet<?>) packet);
	}
	
	public static void sendPacket(Player player, Packet<?> packet) {
		getPlayerConnection(player).a(packet);
	}

	public static EntityPlayer getEntityPlayerFromPlayerConnection(Player player) {
		return getPlayerConnection(player).b;
	}

	public static PlayerConnection getPlayerConnection(Player player) {
		return getEntityPlayer(player).b;
	}

	public static EntityPlayer getEntityPlayer(Player player) {
		return ((CraftPlayer) player).getHandle();
	}
	
	@Override
	public void setArmSwing(Player player, EnumHandUtil hand) {
		switch (hand) {
			case MAIN_HAND:
				setArmSwing(player, EnumHand.a);
				break;
			case OFF_HAND:
				setArmSwing(player, EnumHand.b);
				break;
		}
	}
	
	public void setArmSwing(Player player, EnumHand hand) {
		getPlayerConnection(player).a(new PacketPlayInArmAnimation(hand));
	}
	
	@Override
	public void setHeldItemSlot(Player player, int number) {
		PacketPlayOutHeldItemSlot animation = new PacketPlayOutHeldItemSlot(number);
		sendPacket(player, animation);
	}
	
	@Override
	public void setGameProfile(Player player, GameProfile gameProfile) {
		EntityHuman human = ((CraftPlayer) player).getHandle();
		
		Set<Field> fields = ObjectReflection.getAllFields(human);
		fields.forEach(field -> {
			try {
				if(field.getType().equals(GameProfile.class)) {
					field.setAccessible(true);
					field.set(human, gameProfile);
				}
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public GameProfile getGameProfile(Player player) {
		return getEntityPlayer(player).fp();
	}
	
	@Override
	public void updatePlayer(final Player player) {
		EntityPlayer entityPlayer = PlayerUtilities_Impl.getEntityPlayer(player);

		boolean flying = player.isFlying();
		int entityID = player.getEntityId();
		int heldItem = player.getInventory().getHeldItemSlot();

		PacketPlayOutPlayerInfo packetRemovePlayer = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, entityPlayer);
		PacketPlayOutPlayerInfo packetAddPlayer = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, entityPlayer);
		
		PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityID);
		PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(entityPlayer);
		
//		final PacketPlayOutEntityEquipment headItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(player.getInventory().getHelmet()));
//		final PacketPlayOutEntityEquipment chestItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(player.getInventory().getChestplate()));
//		final PacketPlayOutEntityEquipment legsItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(player.getInventory().getLeggings()));
//		final PacketPlayOutEntityEquipment feetItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.FEET, CraftItemStack.asNMSCopy(player.getInventory().getBoots()));
//		final PacketPlayOutEntityEquipment handItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(player.getInventory().getItemInMainHand()));
//		final PacketPlayOutEntityEquipment offHandItemPacket = new PacketPlayOutEntityEquipment(entityID, EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(player.getInventory().getItemInOffHand()));

		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			final PlayerConnection playerConnection = PlayerUtilities_Impl.getPlayerConnection(player);
			
			if (onlinePlayer == player) {
				playerConnection.a(packetRemovePlayer);
				playerConnection.a(packetAddPlayer);
				Location location = player.getLocation();

//				PacketPlayOutPosition packetPlayOutPosition = new PacketPlayOutPosition(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), new HashSet<>(), 0);
				
				World world = entityPlayer.cA();
				
//				PacketPlayOutRespawn packetPlayOutRespawn = new PacketPlayOutRespawn(
//					world.q_(),
//					world.getDimensionKey(),
//					0, // First 8 bytes of the SHA-256 hash of the world's seed.
//					EnumGamemode.valueOf(player.getGameMode().name()),
//					EnumGamemode.valueOf(player.getGameMode().name()),
//					world.isDebugWorld(),
//					false, // flat world?
//					false // normal respawn? or changed dimension
//				);
				
//				playerConnection.a(packetPlayOutRespawn);
//				playerConnection.a(packetPlayOutPosition);

//				for (MobEffect mobEffect : entityPlayer.getEffects())
//					playerConnection.a(new PacketPlayOutEntityEffect(entityID, mobEffect));

				PacketPlayOutUpdateHealth packetPlayOutUpdateHealth = new PacketPlayOutUpdateHealth((float) player.getHealth(), player.getFoodLevel(), player.getSaturation());
				playerConnection.a(packetPlayOutUpdateHealth);
				
				setHeldItemSlot(player, heldItem);
				
//				entityPlayer.lastSentExp = -1;
				player.updateInventory();
				
				if (flying)
					Bukkit.getPlayer(player.getName()).setFlying(true);
				
				continue;
			}
			
			if (!onlinePlayer.canSee(player))
				continue;
			
			playerConnection.a(packetRemovePlayer);
			playerConnection.a(packetAddPlayer);
			playerConnection.a(packetPlayOutEntityDestroy);
			playerConnection.a(packetPlayOutNamedEntitySpawn);

//			playerConnection.sendPacket(headItemPacket);
//			playerConnection.sendPacket(chestItemPacket);
//			playerConnection.sendPacket(legsItemPacket);
//			playerConnection.sendPacket(feetItemPacket);
//			playerConnection.sendPacket(handItemPacket);
//			playerConnection.sendPacket(offHandItemPacket);
		}
	}
}
