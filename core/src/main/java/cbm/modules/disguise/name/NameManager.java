package cbm.modules.disguise.name;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import cbm.modules.tablist.Tablist;
import cbm.player.PlayerConfig;
import cbm.player.PlayerConfigKey;
import cbm.player.PlayerManager;

public class NameManager {
	public static void nick(Player player, String name) {
		Player toPlayer = name != null ? Bukkit.getPlayer(name) : null;
		String playerListName = null;
		
		PlayerConfig config = PlayerManager.getConfig(player);
		if(name != null) {
			config.setTmp(PlayerConfigKey.nickname, name);
		} else {
			config.delTmp(PlayerConfigKey.nickname);
			name = player.getName();
		}
		
		if(Tablist.isLoaded()) {
			if(toPlayer != null)
				playerListName = Tablist.getTablistName(toPlayer);
			else
				playerListName = Tablist.getTablistName(player);
		}
		
		if(playerListName == null || playerListName.isEmpty())
			playerListName = name;
		
		player.setPlayerListName(playerListName);
		player.setDisplayName(name);
		player.setCustomName(name);
	}
	
	public static void unnick(Player player) {
		nick(player, null);
	}
}
