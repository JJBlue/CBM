package cbm.utilitiesvr.bukkit;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;

import components.reflection.MethodReflection;

public class BukkitUtilitiesReflection {
	private BukkitUtilitiesReflection() {}

	public static SimpleCommandMap getSimpleCommandMap() {
		try {
			return (SimpleCommandMap) MethodReflection.callMethod(getCraftServer(), "getCommandMap");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Server getCraftServer() {
		return Bukkit.getServer();
	}
}
