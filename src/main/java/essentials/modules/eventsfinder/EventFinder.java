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
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import essentials.utilities.chat.ChatUtilities;
import essentials.utilities.chat.HoverAction;
import essentials.utilities.inventory.InventoryFactory;
import essentials.utilities.inventory.InventoryItem;
import essentials.utilities.inventory.InventoryPage;

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
				
				if(commandSender instanceof Player) {
					ChatUtilities.sendChatMessage(
						(Player) commandSender,
						"",
						ChatUtilities.createExtra(
							ChatUtilities.createClickHoverMessage("  " + eventsInformation.event.getSimpleName() + " (x" + methods.size() + ")",
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
	}
	
	//TODO not working correctly -> inventory out of bounds (full)
	public static void inventory(Player player, Collection<EventsInformation> events) {
		InventoryFactory factory = new InventoryFactory(54, "Inventory");
		InventoryPage pluginsPage = factory.createFirstPage();
		final int pluginPagePosition = 0;
		int currentFreePage = 1;
		
		InventoryItem back = new InventoryItem(Material.ARROW);
		back.setDisplayName("§4BACK");
		
		for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
			if(!plugin.isEnabled()) continue;
			
			//Event Page
			InventoryPage eventPage = new InventoryPage();
			final int eventPagePosition = currentFreePage++;
			factory.addInventoryPage(eventPagePosition, eventPage);
			
			//Method Back Button
			InventoryItem backEventClone = back.clone();
			backEventClone.setOnClick((event, item) -> {
				event.setCancelled(true);
				factory.setPage(pluginPagePosition);
			});
			eventPage.addItem(45, backEventClone);
			
			//Plugin Item -> Event page
			InventoryItem pluginItem = new InventoryItem(Material.WHITE_WOOL);
			pluginItem.setDisplayName(plugin.getName());
			pluginItem.setOnClick((event, item) -> {
				event.setCancelled(true);
				factory.setPage(eventPagePosition);
			});
			pluginsPage.addItem(pluginItem);
			
			//Events
			for(EventsInformation eventsInformation : events) {
				
				//Method Page
				InventoryPage methodPage = new InventoryPage();
				final int methodPagePosition = currentFreePage++;
				factory.addInventoryPage(methodPagePosition, methodPage);
				
				//Method Back Button
				InventoryItem backMethodClone = back.clone();
				backMethodClone.setOnClick((event, item) -> {
					event.setCancelled(true);
					factory.setPage(eventPagePosition);
				});
				methodPage.addItem(45, backMethodClone);
				
				//Event Item -> method page
				InventoryItem eventItem = new InventoryItem(Material.WHITE_WOOL);
				eventItem.setDisplayName(eventsInformation.event.getSimpleName());
				eventItem.setOnClick((event, item) -> {
					event.setCancelled(true);
					factory.setPage(methodPagePosition);
				});
				eventPage.addItem(eventItem);
				
				//Methods
				List<Method> methods = eventsInformation.get(plugin);
				if(methods == null || methods.isEmpty()) continue;
				
				for(Method method : methods) {
					InventoryItem methodItem = new InventoryItem(Material.WHITE_WOOL);
					methodItem.setDisplayName(method.getDeclaringClass().getName() + "." + method.getName() + "()");
					methodItem.setOnClick((event, item) -> event.setCancelled(true));
					methodPage.addItem(methodItem);
				}
				
			}
			
		}
		
		factory.refreshPage();
		factory.setDeleteOnExit();
		player.openInventory(factory.getInventory());
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
