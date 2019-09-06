package essentials.player.sudoplayer;

import components.reflections.SimpleReflection;
import essentials.utilitiesvr.ReflectionsUtilities;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class SudoPlayerProxy {
	
	private SudoPlayerProxy() {}
	
	private static Class<?> craftPlayer;
	private static Class<?>[] interfaces;
	
	static {
		try {
			craftPlayer = Class.forName("org.bukkit.craftbukkit." + ReflectionsUtilities.getPackageVersionName() + ".entity.CraftPlayer");
			interfaces = SimpleReflection.getInterfacesArray(craftPlayer);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Player create(CommandSender usedPlayer, Player player) throws ClassNotFoundException {
		if(craftPlayer == null) {
			System.out.println("Error CraftPlayer could not be found. SudoPlayerProxy #1");
			return player;
		}
		
		ClassLoader classLoader = SudoPlayerProxy.class.getClassLoader();
		final AbstractSudoPlayer abstractSudoPlayer = new AbstractSudoPlayer(usedPlayer, player);
		
		Player createdPlayer = (Player) Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
			HashMap<Method, Method> buffer = new HashMap<>();
			
			@Override
			public Object invoke(Object proxy, Method m, Object[] object) throws Throwable {
				Method bufferMethod = buffer.get(m);
				
				if(buffer.containsKey(m))
					m.invoke(player, object);
				else if(bufferMethod != null)
					bufferMethod.invoke(abstractSudoPlayer, object);
				
				try {
					Method otherMethod = AbstractSudoPlayer.class.getMethod(m.getName(), m.getParameterTypes());
					
					if(otherMethod != null) {
						buffer.put(m, otherMethod);
						return otherMethod.invoke(abstractSudoPlayer, object);
					}
				} catch (NoSuchMethodException e) {}
				
				buffer.put(m, null);
				return m.invoke(player, object);
			}
		});
		
		return createdPlayer;
	}

	public static CommandSender create(CommandSender commandSender) {
		if(craftPlayer == null) {
			System.out.println("Error CraftPlayer could not be found. SudoPlayerProxy #2");
			return commandSender;
		}
		
		ClassLoader classLoader = SudoPlayerProxy.class.getClassLoader();
		final AbstractSudoPlayer abstractSudoPlayer = new AbstractSudoPlayer(commandSender);
		
		CommandSender createdPlayer = (CommandSender) Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
			HashMap<Method, Method> buffer = new HashMap<>();
			
			@Override
			public Object invoke(Object proxy, Method m, Object[] object) throws Throwable {
				Method bufferMethod = buffer.get(m);
				
				if(buffer.containsKey(m))
					m.invoke(commandSender, object);
				else if(bufferMethod != null)
					bufferMethod.invoke(abstractSudoPlayer, object);
				
				try {
					Method otherMethod = AbstractSudoPlayer.class.getMethod(m.getName(), m.getParameterTypes());
					
					if(otherMethod != null) {
						buffer.put(m, otherMethod);
						return otherMethod.invoke(abstractSudoPlayer, object);
					}
				} catch (NoSuchMethodException e) {}
				
				buffer.put(m, null);
				return m.invoke(commandSender, object);
			}
		});
		
		return createdPlayer;
	}
}
