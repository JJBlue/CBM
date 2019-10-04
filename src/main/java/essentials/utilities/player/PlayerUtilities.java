package essentials.utilities.player;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import essentials.utilitiesvr.ReflectionsUtilities;
import essentials.utilitiesvr.player.PlayerUtilitiesReflections;
import essentials.utilitiesvr.player.PlayerUtilities_v1_14;

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
	
	public static void updatePlayer(Player p) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				PlayerUtilities_v1_14.updatePlayer(p);
				return;
		}
		PlayerUtilitiesReflections.updatePlayer(p);
	}
	
	public static String getName(Player player) {
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
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
		}
		
		PlayerUtilitiesReflections.setArmSwing(player, hand);
	}
	
	public static void setHeldItemSlot(Player player, int number) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				PlayerUtilities_v1_14.setHeldItemSlot(player, number);
				return;
		}
		
		PlayerUtilitiesReflections.setHeldItemSlot(player, number);
	}
}
