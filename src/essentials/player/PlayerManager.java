package essentials.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerManager {
	private PlayerManager() {}
	
	static Map<UUID, PlayerConfig> players = Collections.synchronizedMap(new HashMap<>());
	
	public static PlayerConfig getPlayerConfig(Player player) {
		return getPlayerConfig(player.getUniqueId());
	}
	
	public static synchronized PlayerConfig getPlayerConfig(UUID uuid) {
		PlayerConfig playerConfig = players.get(uuid);
		
		if(playerConfig != null)
			return playerConfig;
		
		Player player = Bukkit.getPlayer(uuid);
		if(player != null && player.isOnline())
			return load(uuid, true);
		return load(uuid, false);
	}
	
	synchronized static PlayerConfig load(UUID uuid, boolean buffer) {
		PlayerConfig playerConfig = new PlayerConfig(uuid);
		if(buffer)
			players.put(uuid, playerConfig);
		return playerConfig;
	}
	
	synchronized static void unload(UUID uuid) {
		PlayerConfig playerConfig = players.remove(uuid);
		playerConfig.save();
	}
}
