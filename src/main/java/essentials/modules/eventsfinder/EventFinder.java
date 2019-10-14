package essentials.modules.eventsfinder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import essentials.utilities.chat.ChatUtilities;
import essentials.utilities.chat.HoverAction;

public class EventFinder {
	public static void print(CommandSender commandSender) {
		print(commandSender, findEvents());
	}
	
	public static void print(CommandSender commandSender, Plugin plugin) {
		print(commandSender, findEvents(plugin));
	}
	
	/*
		 Plugin Name:
		  Event 1 (Anzahl)
		  Event 2 (Anzahl) 
		  ...
	 
		 Plugin Name:
		  Event 1:
		    Methode 1
		 ...
	 
	 */
	public static void print(CommandSender commandSender, Collection<EventsInformation> events) {
		//TODO
		
		for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
			boolean foundInformation = false;
			
			for(EventsInformation eventsInformation : events) {
				List<Method> methods = eventsInformation.get(plugin);
				if(methods == null) continue;
				
				if(!foundInformation) {
					foundInformation = true;
					commandSender.sendMessage(plugin.getName() + ":");
				}
				
				List<String> methodsHover = new LinkedList<>();
				StringBuilder hovertext = new StringBuilder();
				
				for(Method method : methods) {
					String tmp = method.getDeclaringClass().getName() + "." + method.getName() + "()";
					if(!methodsHover.contains(tmp)) {
						
						if(!methodsHover.isEmpty())
							hovertext.append('\n');
						hovertext.append(tmp);
						
						methodsHover.add(tmp);
					}
				}
				
				ChatUtilities.sendChatMessage(
					(Player) commandSender,
					"",
					ChatUtilities.createExtra(
						ChatUtilities.createClickHoverMessage("  " + eventsInformation.event.getSimpleName() + " x" + methods.size(),
							HoverAction.SHOW_Text,
							hovertext.toString(),
							null,
							null
						)
					)
				);
			}
		}
	}
	
	public static Collection<EventsInformation> findEvents() {
		List<HandlerList> handlerLists = HandlerList.getHandlerLists();
		
		Set<Listener> listeners = new HashSet<>();
		Map<Class<?>, EventsInformation> events = new HashMap<>();
		
		for(HandlerList handlerList : handlerLists) {
			RegisteredListener[] registeredListeners = handlerList.getRegisteredListeners();
			
			for(RegisteredListener registeredListener : registeredListeners) {
				Listener listener = registeredListener.getListener();
				
				if(listeners.contains(listener)) continue;
				listeners.add(listener);
				
				Plugin plugin = registeredListener.getPlugin();
				parseListener(events, plugin, listener);
			}
		}
		
		return events.values();
	}
	
	public static Collection<EventsInformation> findEvents(Plugin plugin) {
		Set<Listener> listeners = new HashSet<>();
		Map<Class<?>, EventsInformation> events = new HashMap<>();
		
		for(RegisteredListener registeredListener : HandlerList.getRegisteredListeners(plugin)) {
			Listener listener = registeredListener.getListener();
			
			if(listeners.contains(listener)) continue;
			listeners.add(listener);
			
			parseListener(events, plugin, listener);
		}
		
		return events.values();
	}
	
	public static void parseListener(Map<Class<?>, EventsInformation> events, Plugin plugin, Listener listener) {
		Class<?> listenerClass = listener.getClass();
		
		for(Method method : listenerClass.getMethods()) {
			if(method.getAnnotations().length <= 0) continue;
			
			for(Annotation annotation : method.getAnnotations()) {
				if(!(annotation.annotationType() == EventHandler.class)) continue;
				if(method.getParameters().length <= 0) continue;
				
				for(Parameter parameter : method.getParameters()) {
					
					if(!events.containsKey(parameter.getType()))
						events.put(parameter.getType(), new EventsInformation(parameter.getType()));
					
					EventsInformation eventsInformation = events.get(parameter.getType());
					eventsInformation.add(plugin, method);
					
				}
			}
		}
	}
}
