package cbm.depend;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import cbm.depend.vault.Vault;
import cbm.player.PlayersYMLConfig;
import cbm.utilities.placeholder.PlaceholderFormatter;
import cbm.utilitiesvr.chat.ChatUtilities;
import cbm.utilitiesvr.player.PlayerUtilities;

public class Depend {
	public static boolean existVault() {
		return Bukkit.getPluginManager().getPlugin("Vault") != null;
	}
	
	public static String getVaultPrefix(Player player) {
		if(Depend.existVault()) {
			Player player2 = Bukkit.getPlayer(PlayerUtilities.getName(player));
			return ChatUtilities.convertToColor(Vault.getPrefix(player2 != null ? player2 : player));
		}
		
		return "";
	}
	
	public static String getPrefix(Player player) {
		StringBuilder prefix = new StringBuilder();
		
		ConfigurationSection section = PlayersYMLConfig.getConfigurationSection("chat");
		if(section != null)
			prefix.append(ChatUtilities.convertToColor(PlaceholderFormatter.setPlaceholders(player, section.getString("prefix"))));
	
		prefix.append(getVaultPrefix(player));
		
		return prefix.toString();
	}
	
	public static String getVaultSuffix(Player player) {
		if(Depend.existVault()) {
			Player player2 = Bukkit.getPlayer(PlayerUtilities.getName(player));
			return ChatUtilities.convertToColor(Vault.getSuffix(player2 != null ? player2 : player));
		}
		
		return "";
	}
	
	public static String getSuffix(Player player) {
		StringBuilder suffix = new StringBuilder();
		
		ConfigurationSection section = PlayersYMLConfig.getConfigurationSection("chat");
		if(section != null)
			suffix.append(ChatUtilities.convertToColor(PlaceholderFormatter.setPlaceholders(player, section.getString("suffix"))));
	
		suffix.append(getVaultSuffix(player));
		
		return suffix.toString();
	}
}
