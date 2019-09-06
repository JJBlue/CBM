package essentials.connection.eventmanager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectionEventManager {
	private ConnectionEventManager() {}
	
	private static CopyOnWriteArrayList<Listener> listeners;
	
	static {
		listeners = new CopyOnWriteArrayList<>();
	}
	
	protected static void clearAll(){
		if(!listeners.isEmpty())
			listeners.clear();
	}
	
	public static void register(Listener l) {
		if(!listeners.contains(l)){
			listeners.add(l);
		}
	}
	
	public static void unregister(Listener l) {
		if(!listeners.isEmpty() && listeners.contains(l)){
			listeners.remove(l);
		}
	}
	
	public static void call(ConnectionEvent event) {
		call(event, EventHandler.class);
	}
	
	protected static void call(ConnectionEvent event, Class<? extends Annotation> EH) {
		Iterator<Listener> it = listeners.iterator();
		// Alle Listener iterieren
		while (it.hasNext()) {
			Listener l = it.next();
			
			for (Method m : l.getClass().getMethods()) {
				if (m.isAnnotationPresent(EH)) {
					if (m.getParameterTypes().length == 1 && m.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
						try {
							m.invoke(l, event);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
							System.out.println("Class: " + l.getClass().getName());
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
							System.out.println("Class: " + l.getClass().getName());
						} catch (InvocationTargetException e) {
							e.printStackTrace();
							System.out.println("Class: " + l.getClass().getName());
						}
					}
				}
			}
		}
	}
}