package essentials.utilitiesvr.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;

public class BukkitUtilities_v1_14 {
	private BukkitUtilities_v1_14() {}
	
	public static SimpleCommandMap getSimpleCommandMap() {
		return getCraftServer().getCommandMap();
	}
	
	public static CraftServer getCraftServer() {
		return ((CraftServer) Bukkit.getServer());
	}
}
