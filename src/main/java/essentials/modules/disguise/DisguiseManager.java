package essentials.modules.disguise;

import java.io.IOException;

import org.bukkit.entity.Player;

import essentials.modules.disguise.gameprofile.GameProfileBuilder;
import essentials.utilities.player.PlayerUtilities;

public class DisguiseManager {
	public static void disguise(Player player, String name) {
//		NameManager.nick(player, name);
		
		try {
			PlayerUtilities.setGameProfile(player, GameProfileBuilder.fetch(PlayerUtilities.getOfflinePlayer(name).getUniqueId()));
			PlayerUtilities.updatePlayer(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void undisguise(Player player) {
//		NameManager.unnick(player);
		
		try {
			PlayerUtilities.setGameProfile(player, GameProfileBuilder.fetch(player.getUniqueId()));
			PlayerUtilities.updatePlayer(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
