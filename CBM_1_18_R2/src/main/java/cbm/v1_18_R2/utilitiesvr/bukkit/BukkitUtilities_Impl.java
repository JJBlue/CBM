package cbm.v1_18_R2.utilitiesvr.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;

import cbm.utilitiesvr.bukkit.BukkitUtilities_Interface;

public class BukkitUtilities_Impl implements BukkitUtilities_Interface {
	@Override
	public SimpleCommandMap getSimpleCommandMap() {
		return getCraftServer().getCommandMap();
	}

	public static CraftServer getCraftServer() {
		return ((CraftServer) Bukkit.getServer());
	}
}
