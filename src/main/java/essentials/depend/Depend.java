package essentials.depend;

import org.bukkit.Bukkit;

public class Depend {
	public static boolean existVault() {
		return Bukkit.getPluginManager().getPlugin("Vault") != null;
	}
}
