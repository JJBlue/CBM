package cbm.modules.disguise;

import java.io.IOException;

import org.bukkit.entity.Player;

import cbm.language.LanguageConfig;
import cbm.modules.disguise.gameprofile.GameProfileBuilder;
import cbm.modules.disguise.name.NameManager;
import cbm.utilitiesvr.player.PlayerUtilities;

public class DisguiseManager {
	public static void disguise(Player player, String name) {
		try {
			PlayerUtilities.setGameProfile(player, GameProfileBuilder.fetch(PlayerUtilities.getOfflinePlayer(name).getUniqueId(), player.getUniqueId(), name, false));
			NameManager.nick(player, name);
			PlayerUtilities.updatePlayer(player);
		} catch (IOException e) {
			LanguageConfig.sendMessage(player, "skin.error-load");
			return;
		}
	}
	
	public static void undisguise(Player player) {
		try {
			PlayerUtilities.setGameProfile(player, GameProfileBuilder.fetch(player.getUniqueId()));
			NameManager.unnick(player);
			PlayerUtilities.updatePlayer(player);
		} catch (IOException e) {
			LanguageConfig.sendMessage(player, "skin.error-load");
			return;
		}
	}
}
