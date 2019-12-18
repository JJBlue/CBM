package essentials.modules.disguise.name;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import essentials.modules.tablist.Tablist;
import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;

public class NameManager {
	public static void nick(Player player, String name) {
		Player toPlayer = name != null ? Bukkit.getPlayer(name) : null;
		String playerListName = null;
		
		PlayerConfig config = PlayerManager.getConfig(player);
		if(name != null)
			config.setTmp("nick", name);
		else {
			config.removeBuffer("nick");
			name = player.getName();
		}
		
		if(toPlayer != null)
			playerListName = Tablist.getTablistName(toPlayer);
		else
			playerListName = Tablist.getTablistName(player);
		
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
