package essentials.utilitiesvr.player;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import components.reflections.SimpleReflection;

public class PlayerUtilitiesReflections {
	public static void sendPacket(Player player, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchFieldException {
		Object playerConnection = getPlayerConnection(player);
		SimpleReflection.callMethod(playerConnection, "sendPacket", obj);
	}
	
	public static Object getPlayerConnection(Player player) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchFieldException {
		return SimpleReflection.getObject("playerConnection", SimpleReflection.callMethod(player, "getHandle"));
	}
}
