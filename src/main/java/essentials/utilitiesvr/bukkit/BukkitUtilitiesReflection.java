package essentials.utilitiesvr.bukkit;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;

import components.reflections.SimpleReflection;

public class BukkitUtilitiesReflection {
	private BukkitUtilitiesReflection() {}

	public static SimpleCommandMap getSimpleCommandMap() {
		try {
			return (SimpleCommandMap) SimpleReflection.callMethod(getCraftServer(), "getCommandMap");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Server getCraftServer() {
		return Bukkit.getServer();
	}
}
