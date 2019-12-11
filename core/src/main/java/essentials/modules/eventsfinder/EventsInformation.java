package essentials.modules.eventsfinder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

public class EventsInformation {
	final Class<?> event;
	Map<Plugin, List<Method>> eventHandlers;
	
	public EventsInformation(Class<?> event) {
		this.event = event;
	}

	public void add(Plugin plugin, Method method) {
		if(eventHandlers == null)
			eventHandlers = new HashMap<>();
		
		List<Method> methods = eventHandlers.get(plugin);
		if(methods == null) {
			methods = new LinkedList<>();
			eventHandlers.put(plugin, methods);
		}
		
		methods.add(method);
	}
	
	public List<Method> get(Plugin plugin) {
		return eventHandlers.get(plugin);
	}
	
	public static boolean isIgnoredMethod(Method method) {
		return method.getAnnotation(EventHandler.class).ignoreCancelled();
	}
	
	public static EventPriority getEventPriority(Method method) {
		return method.getAnnotation(EventHandler.class).priority();
	}

	public boolean containsPlugin(Plugin plugin) {
		return eventHandlers.containsKey(plugin);
	}
	
	public String getEventName() {
		return event.getSimpleName();
	}
}
