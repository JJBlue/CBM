package essentials.utilities;

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
import net.minecraft.server.v1_14_R1.PacketPlayOutBlockBreakAnimation;

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

	public static void sendPacket(Player player, PacketPlayOutBlockBreakAnimation packet) {
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
}
