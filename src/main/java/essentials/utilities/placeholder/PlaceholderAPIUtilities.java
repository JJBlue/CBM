package essentials.utilities.placeholder;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class PlaceholderAPIUtilities {
	public static String set(Player player, String text) {
		return PlaceholderAPI.setPlaceholders(player, text);
	}
}
