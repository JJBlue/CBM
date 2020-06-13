package cbm.utilitiesvr.player;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import cbm.player.PlayerConfig;
import cbm.player.PlayerManager;
import cbm.versions.VersionDependency;
import cbm.versions.minecraft.MinecraftVersions;

public class PlayerUtilities {
	public final static VersionDependency<PlayerUtilities_Interface> version_dependency = new VersionDependency<>();
	
	@SuppressWarnings("deprecation")
	public static OfflinePlayer getOfflinePlayer(String name) {
		return Bukkit.getOfflinePlayer(name);
	}

	public static OfflinePlayer getOfflinePlayerFromUUID(String uuidString) {
		UUID uuid = null;
		try {
			uuid = UUID.fromString(uuidString);
		} catch (IllegalArgumentException e) {}

		if (uuid == null) return null;
		return Bukkit.getOfflinePlayer(uuid);
	}
	
	public static UUID getUUID(String player) {
		return getOfflinePlayer(player).getUniqueId();
	}
	
	public static void updatePlayer(Player p) {
		PlayerUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		if(vd != null) {
			vd.updatePlayer(p);
			return;
		}
		PlayerUtilitiesReflections.updatePlayer(p);
	}
	
	public static String getName(Player player) {
		PlayerConfig config = PlayerManager.getConfig(player);
		if(config == null) return player.getName();
		
		if(config.containsLoadedKey("nick"))
			return config.getString("nick");
		
		return player.getName();
	}

	public static void sendPacket(Player player, Object packet) {
		PlayerUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		if(vd != null) {
			vd.sendPacket(player, packet);
			return;
		}
		
		try {
			PlayerUtilitiesReflections.sendPacket(player, packet);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public static void setArmSwing(Player player, EnumHandUtil hand) {
		PlayerUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		if(vd != null) {
			vd.setArmSwing(player, hand);
			return;
		}
		
		PlayerUtilitiesReflections.setArmSwing(player, hand);
	}
	
	public static void setHeldItemSlot(Player player, int number) {
		PlayerUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		if(vd != null)
			vd.setHeldItemSlot(player, number);
		PlayerUtilitiesReflections.setHeldItemSlot(player, number);
	}
	
	public static void setGameProfile(Player player, GameProfile gameProfile) {
		PlayerUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		if(vd != null)
			vd.setGameProfile(player, gameProfile);
		PlayerUtilitiesReflections.setGameProfile(player, gameProfile);
	}
	
	public static GameProfile getGameProfile(Player player) {
		PlayerUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		if(vd != null)
			vd.getGameProfile(player);
		return PlayerUtilitiesReflections.getGameProfile(player);
	}
}
