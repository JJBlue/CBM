package essentials.modules.disguise;

import java.io.IOException;

import org.bukkit.entity.Player;

import essentials.modules.disguise.gameprofile.GameProfileBuilder;
import essentials.modules.disguise.name.NameManager;
import essentials.utilities.player.PlayerUtilities;

public class DisguiseManager {
	public static void disguise(Player player, String name) {
		try {
			PlayerUtilities.setGameProfile(player, GameProfileBuilder.fetch(PlayerUtilities.getOfflinePlayer(name).getUniqueId(), player.getUniqueId(), name, false));
			NameManager.nick(player, name);
			PlayerUtilities.updatePlayer(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void undisguise(Player player) {
		try {
			PlayerUtilities.setGameProfile(player, GameProfileBuilder.fetch(player.getUniqueId()));
			NameManager.unnick(player);
			PlayerUtilities.updatePlayer(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
