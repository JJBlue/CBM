package essentials.modules.disguise.name;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import essentials.modules.tablist.Tablist;
import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;

public class NameManager {
	public static void nick(Player player, String name) {
		Player toPlayer = Bukkit.getPlayer(name);
		String playerListName = null;
		
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		config.setTmp("nick", name);
		
		if(toPlayer != null)
			playerListName = Tablist.getTablistName(toPlayer);
		else if(playerListName == null)
			playerListName = Tablist.getTablistName(player);
		
		if(playerListName == null || playerListName.isEmpty())
			playerListName = name;
		
		player.setPlayerListName(playerListName);
		player.setDisplayName(name);
		player.setCustomName(name);
	}
	
	public static void unnick(Player player) {
		nick(player, player.getName());
	}
}
