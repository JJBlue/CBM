package essentials.modules.eventsfinder;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
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
	public static void inventory(Player player, Collection<EventsInformation> eventsCollection) {
		List<EventsInformation> events = new LinkedList<>(eventsCollection);
		events.sort(Comparator.comparing(EventsInformation::getEventName));
		
		InventoryFactory mainFactory = new InventoryFactory(54, "Event Finder");
		
		InventoryItem previousPage = new InventoryItem(Material.ARROW);
		previousPage.setDisplayName("§4Back to other menu"); //TODO language
		
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
			addItemStack(eventFactory, 46, previousPage);
			
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
				addItemStack(methodFactory, 46, previousPage);
				
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
			back.setDisplayName("Previous Page"); //TODO language
			back.setOnClick((event, item) -> {
				event.setCancelled(true);
				factory.previous();
			});
			page.addItem(45, back);
			
			InventoryItem next = new InventoryItem(Material.ARROW);
			next.setDisplayName("Next Page"); //TODO language
			next.setOnClick((event, item) -> {
				event.setCancelled(true);
				factory.next();
			});
			page.addItem(53, next);
			
			if(factory.getSize() != 1)
				page.addItem(46, factory.getPage(0).get(46).clone());
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
			page.addItem(45, back);
			
			InventoryItem next = new InventoryItem(Material.ARROW);
			next.setDisplayName("Next Page");
			next.setOnClick((event, item) -> {
				event.setCancelled(true);
				factory.next();
			});
			page.addItem(53, next);
			
			if(factory.getSize() != 1)
				page.addItem(46, factory.getPage(0).get(46).clone());
		}
		
		page.addItem(slot, itemStack);
	}
	
	protected static Material getMaterial(String eventName) {
		eventName = eventName.toLowerCase();
		
		if(eventName.contains("inventory"))
			return Material.CHEST;
		if(eventName.contains("block"))
			return Material.DIAMOND_PICKAXE;
		if(eventName.contains("damage"))
			return Material.DIAMOND_SWORD;
		if(eventName.contains("move"))
			return Material.DIAMOND_BOOTS;
		if(eventName.contains("projectile"))
			return Material.ARROW;
		if(eventName.contains("player"))
			return Material.PLAYER_HEAD;
		if(eventName.contains("entity"))
			return Material.ZOMBIE_HEAD;
		if(eventName.contains("creature"))
			return Material.CREEPER_HEAD;
		if(eventName.contains("bow"))
			return Material.BOW;
		if(eventName.contains("craft"))
			return Material.CRAFTING_TABLE;
		if(eventName.contains("anvil"))
			return Material.ANVIL;
		if(eventName.contains("command") || eventName.contains("plugin"))
			return Material.COMMAND_BLOCK;
		if(eventName.contains("vehicle"))
			return Material.OAK_BOAT;
		if(eventName.contains("potion"))
			return Material.GLOWSTONE_DUST; //potion
		if(eventName.contains("portal"))
			return Material.NETHER_PORTAL;
		if(eventName.contains("xp"))
			return Material.EXPERIENCE_BOTTLE;
		if(eventName.contains("chunk"))
			return Material.GRASS_BLOCK;
		if(eventName.contains("world"))
			return Material.MAP; //TODO world head /give @p minecraft:player_head{display:{Name:"{\"text\":\"Globe\"}"},SkullOwner:"BlockminersTV"}
		if(eventName.contains("explosion"))
			return Material.TNT;
		return Material.WHITE_WOOL;
	}
}
