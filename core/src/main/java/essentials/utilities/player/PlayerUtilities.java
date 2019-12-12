package essentials.utilities.player;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import essentials.utilities.minecraft.ReflectionsUtilities;
import essentials.utilitiesvr.player.PlayerUtilitiesReflections;
import essentials.utilitiesvr.player.PlayerUtilities_v1_14;
import essentials.utilitiesvr.player.PlayerUtilities_v1_15;

public class PlayerUtilities {
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
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				PlayerUtilities_v1_14.updatePlayer(p);
				return;
			case "v1_15_R1":
				PlayerUtilities_v1_15.updatePlayer(p);
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
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				PlayerUtilities_v1_14.sendPacket(player, packet);
				return;
			case "v1_15_R1":
				PlayerUtilities_v1_15.sendPacket(player, packet);
				return;
		}
		
		try {
			PlayerUtilitiesReflections.sendPacket(player, packet);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public static void setArmSwing(Player player, EnumHandUtil hand) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				PlayerUtilities_v1_14.setArmSwing(player, hand);
				return;
			case "v1_15_R1":
				PlayerUtilities_v1_15.setArmSwing(player, hand);
				return;
		}
		
		PlayerUtilitiesReflections.setArmSwing(player, hand);
	}
	
	public static void setHeldItemSlot(Player player, int number) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				PlayerUtilities_v1_14.setHeldItemSlot(player, number);
				return;
			case "v1_15_R1":
				PlayerUtilities_v1_15.setHeldItemSlot(player, number);
				return;
		}
		
		PlayerUtilitiesReflections.setHeldItemSlot(player, number);
	}
	
	public static void setGameProfile(Player player, GameProfile gameProfile) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				PlayerUtilities_v1_14.setGameProfile(player, gameProfile);
				return;
			case "v1_15_R1":
				PlayerUtilities_v1_15.setGameProfile(player, gameProfile);
				return;
		}
		
		PlayerUtilitiesReflections.setGameProfile(player, gameProfile);
	}
	
	public static GameProfile getGameProfile(Player player) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				return PlayerUtilities_v1_14.getGameProfile(player);
			case "v1_15_R1":
				return PlayerUtilities_v1_14.getGameProfile(player);
		}
		
		return PlayerUtilitiesReflections.getGameProfile(player);
	}
}
