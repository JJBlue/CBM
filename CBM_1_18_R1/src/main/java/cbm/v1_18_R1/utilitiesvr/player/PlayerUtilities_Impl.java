package cbm.v1_18_R1.utilitiesvr.player;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;

import cbm.utilitiesvr.player.EnumHandUtil;
import cbm.utilitiesvr.player.PlayerUtilities_Interface;
import components.reflection.ObjectReflection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayInArmAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEffect;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.protocol.game.PacketPlayOutPosition;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateHealth;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.EnumHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EnumItemSlot;
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
		
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			final PlayerConnection playerConnection = PlayerUtilities_Impl.getPlayerConnection(player);
			
			if (onlinePlayer == player) {
				playerConnection.a(packetRemovePlayer);
				playerConnection.a(packetAddPlayer);
				World world = entityPlayer.cA();
				PacketPlayOutRespawn packetPlayOutRespawn = new PacketPlayOutRespawn(
					world.q_(),
					world.aa(),
					player.getWorld().getSeed(), // First 8 bytes of the SHA-256 hash of the world's seed.
					entityPlayer.d.b(),
					entityPlayer.d.b(),
					world.ad(), // debug world?
					false, // flat world?
					false // normal respawn? or changed dimension
				);
				playerConnection.a(packetPlayOutRespawn);
				
				Location location = player.getLocation();
				PacketPlayOutPosition packetPlayOutPosition = new PacketPlayOutPosition(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), new HashSet<>(), 0, false); // 0 = TeleportConfirm, false = Do not leave vehicle
				playerConnection.a(packetPlayOutPosition);

				for (MobEffect mobEffect : entityPlayer.dW())
					playerConnection.a(new PacketPlayOutEntityEffect(entityID, mobEffect));

				PacketPlayOutUpdateHealth packetPlayOutUpdateHealth = new PacketPlayOutUpdateHealth((float) player.getHealth(), player.getFoodLevel(), player.getSaturation());
				playerConnection.a(packetPlayOutUpdateHealth);
				
				setHeldItemSlot(player, heldItem);
				
				player.updateInventory();
				
				if (flying)
					Bukkit.getPlayer(player.getName()).setFlying(true);
				
				continue;
			}
			
			if (!onlinePlayer.canSee(player))
				continue;
			
			PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityID);
			PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(entityPlayer);

			final PacketPlayOutEntityEquipment itemPacket = new PacketPlayOutEntityEquipment(entityID,
				Stream.of(
					Pair.of(EnumItemSlot.a, CraftItemStack.asNMSCopy(player.getInventory().getItemInMainHand())),
					Pair.of(EnumItemSlot.b, CraftItemStack.asNMSCopy(player.getInventory().getItemInOffHand())),
					Pair.of(EnumItemSlot.c, CraftItemStack.asNMSCopy(player.getInventory().getBoots())),
					Pair.of(EnumItemSlot.d, CraftItemStack.asNMSCopy(player.getInventory().getLeggings())),
					Pair.of(EnumItemSlot.e, CraftItemStack.asNMSCopy(player.getInventory().getChestplate())),
					Pair.of(EnumItemSlot.f, CraftItemStack.asNMSCopy(player.getInventory().getHelmet()))
				).collect(Collectors.toList())
			);
			
			playerConnection.a(packetRemovePlayer);
			playerConnection.a(packetAddPlayer);
			playerConnection.a(packetPlayOutEntityDestroy);
			playerConnection.a(packetPlayOutNamedEntitySpawn);
			playerConnection.a(itemPacket);
		}
	}
}
