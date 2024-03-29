package cbm.connection.eventmanager;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ConnectionEventManager {
	private ConnectionEventManager() {}

	private static CopyOnWriteArrayList<Listener> listeners;

	static {
		listeners = new CopyOnWriteArrayList<>();
	}

	protected static void clearAll() {
		if (!listeners.isEmpty())
			listeners.clear();
	}

	public static void register(Listener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	public static void unregister(Listener l) {
		if (!listeners.isEmpty()) {
			listeners.remove(l);
		}
	}

	public static void call(ConnectionEvent event) {
		call(event, EventHandler.class);
	}

	protected static void call(ConnectionEvent event, Class<? extends Annotation> EH) {
		// Alle Listener iterieren
		for (Listener l : listeners) {
			for (Method m : l.getClass().getMethods()) {
				if (m.isAnnotationPresent(EH)) {
					if (m.getParameterTypes().length == 1 && m.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
						try {
							m.invoke(l, event);
						} catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
							e.printStackTrace();
							System.out.print("Class: " + l.getClass().getName());
						}
					}
				}
			}
		}
	}
}