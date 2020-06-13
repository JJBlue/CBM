package cbm.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TablistUtilities {
	public static void sendHeaderFooter(String header, String footer) {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers())
			onlinePlayer.setPlayerListHeaderFooter(header, footer);
	}

	public static void sendHeaderFooter(Player player, String header, String footer) {
		player.setPlayerListHeaderFooter(header, footer);
	}

	public static void sendPlayerNameHeaderFooter(Player player, String playerName, String header, String footer) {
		player.setPlayerListName(playerName);
		sendHeaderFooter(player, header, footer);
	}
}
