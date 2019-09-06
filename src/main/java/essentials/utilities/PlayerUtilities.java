package essentials.utilities;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

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
		
		if(uuid == null) return null;
		return Bukkit.getOfflinePlayer(uuid);
	}
}
