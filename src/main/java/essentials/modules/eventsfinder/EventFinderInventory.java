package essentials.modules.eventsfinder;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import essentials.utilities.inventory.InventoryFactory;
import essentials.utilities.inventory.InventoryItem;
import essentials.utilities.inventory.InventoryPage;

public class EventFinderInventory {
	public static void inventory(Player player, Collection<EventsInformation> events) {
		InventoryFactory mainFactory = new InventoryFactory(54, "Event Finder");
		
		InventoryItem previousPage = new InventoryItem(Material.ARROW);
		previousPage.setDisplayName("Previous Page");
		
		for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
			if(!plugin.isEnabled()) continue;
			
			//Event Factory
			InventoryFactory eventFactory = new InventoryFactory(54, plugin.getName() + " Events");
			
			//Back button
			previousPage = previousPage.clone();
			previousPage.setOnClick((event, item) -> {
				event.setCancelled(true);
				mainFactory.openInventory(player);
			});
			addItemStack(eventFactory, 45, previousPage);
			
			//Plugin Item -> Event Factory
			InventoryItem pluginItem = new InventoryItem(Material.WHITE_WOOL);
			pluginItem.setDisplayName(plugin.getName());
			pluginItem.setOnClick((event, item) -> {
				event.setCancelled(true);
				eventFactory.openInventory(player);
			});
			addItemStack(mainFactory, pluginItem);
			
			//Events
			for(EventsInformation eventsInformation : events) {
				
				if(!eventsInformation.containsPlugin(plugin)) continue;
				
				//Method Factory
				InventoryFactory methodFactory = new InventoryFactory(54, plugin.getName() + " - " + eventsInformation.getEventName());
				
				//Back button
				previousPage = previousPage.clone();
				previousPage.setOnClick((event, item) -> {
					event.setCancelled(true);
					eventFactory.openInventory(player);
				});
				addItemStack(methodFactory, 45, previousPage);
				
				//Event Item -> method Factory
				InventoryItem eventItem = new InventoryItem(getMaterial(eventsInformation.getEventName()));
				eventItem.setDisplayName(eventsInformation.getEventName());
				eventItem.setOnClick((event, item) -> {
					event.setCancelled(true);
					methodFactory.openInventory(player);
				});
				addItemStack(eventFactory, eventItem);
				
				//Methods
				List<Method> methods = eventsInformation.get(plugin);
				if(methods == null || methods.isEmpty()) continue;
				
				for(Method method : methods) {
					InventoryItem methodItem = new InventoryItem(Material.WHITE_WOOL);
					methodItem.setDisplayName(method.getDeclaringClass().getName() + "." + method.getName() + "()");
					methodItem.setOnClick((event, item) -> event.setCancelled(true));
					addItemStack(methodFactory, methodItem);
				}
				
			}
			
		}
		
		mainFactory.openInventory(player);
	}
	
	protected static void addItemStack(InventoryFactory factory, ItemStack itemStack) {
		InventoryPage page = factory.getPage(factory.getSize() - 1);
		
		if(page == null || page.count(0, 44) >= 45) {
			page = new InventoryPage();
			factory.addInventoryPage(page);
			
			InventoryItem back = new InventoryItem(Material.ARROW);
			back.setDisplayName("Previous Page");
			back.setOnClick((event, item) -> {
				event.setCancelled(true);
				factory.previous();
			});
			page.addItem(46, back);
			
			InventoryItem next = new InventoryItem(Material.ARROW);
			next.setDisplayName("Next Page");
			next.setOnClick((event, item) -> {
				event.setCancelled(true);
				factory.next();
			});
			page.addItem(53, next);
			
			if(factory.getSize() != 1)
				page.addItem(45, factory.getPage(0).get(45).clone());
		}
		
		page.addItem(itemStack);
	}
	
	protected static void addItemStack(InventoryFactory factory, int slot, ItemStack itemStack) {
		InventoryPage page = factory.getPage(factory.getSize() - 1);
		
		if(page == null || page.count(0, 44) >= 45) {
			page = new InventoryPage();
			factory.addInventoryPage(page);
			
			InventoryItem back = new InventoryItem(Material.ARROW);
			back.setDisplayName("Previous Page");
			back.setOnClick((event, item) -> {
				event.setCancelled(true);
				factory.previous();
			});
			page.addItem(46, back);
			
			InventoryItem next = new InventoryItem(Material.ARROW);
			next.setDisplayName("Next Page");
			next.setOnClick((event, item) -> {
				event.setCancelled(true);
				factory.next();
			});
			page.addItem(53, next);
			
			if(factory.getSize() != 1)
				page.addItem(45, factory.getPage(0).get(45).clone());
		}
		
		page.addItem(slot, itemStack);
	}
	
	protected static Material getMaterial(String eventName) {
		eventName = eventName.toLowerCase();
		
		Bukkit.broadcastMessage(eventName);
		
		if(eventName.contains("inventory"))
			return Material.CHEST;
		if(eventName.contains("damage"))
			return Material.DIAMOND_SWORD;
		if(eventName.contains("block"))
			return Material.DIAMOND_PICKAXE;
		if(eventName.contains("move"))
			return Material.DIAMOND_BOOTS;
		return Material.WHITE_WOOL;
	}
}
