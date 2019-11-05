package essentials.modulemanager;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import essentials.main.Main;
import essentials.modules.commandspy.CommandSpyModule;
import essentials.modules.debugstick.DebugStickModule;

public class ModuleManager {

	static Map<String, Module> modules;
	static Map<Module, List<Listener>> listeners;
	
	public static void load() {
		if(modules != null) return;
		
		ModuleConfig.load();
		
		modules = Collections.synchronizedMap(new HashMap<>());
		listeners = Collections.synchronizedMap(new HashMap<>());
		
		addDefaultModules();
		
		synchronized (modules) {
			for(Module module : modules.values()) {
				if(ModuleConfig.shouldLoadModule(module.getID())) {
					enable(module);
				}
			}
		}
	}
	
	public static void addDefaultModules() {
		addModule(new DebugStickModule());
		addModule(new CommandSpyModule());
	}
	
	public static void unload() {
		//TODO try catch
		if(modules == null) return;
		
		synchronized (modules) {
			for(Module module : modules.values()) {
				if(module.isLoaded()) {
					module.disable();
				}
			}
			
			modules = null;
		}
	}
	
	public static void enable(Module module) {
		if(module.isLoaded()) return;
		module.enable();
	}
	
	public static void disable(Module module) {
		if(!module.isLoaded()) return;
		module.disable();
		unloadListeners(module);
	}
	
	public static void addModule(Module module) {
		synchronized (module) {
			if(!modules.containsKey(module.getID()))
				modules.put(module.getID(), module);
		}
	}
	
	public static void removeModule(Module module) {
		disable(module);
		modules.remove(module.getID());
	}
	
	public static Module getModule(String id) {
		return modules.get(id);
	}
	
	public static void addListener(Listener listener, Module module) {
		List<Listener> list = listeners.get(module);
		if(list == null) {
			list = Collections.synchronizedList(new LinkedList<>());
			listeners.put(module, list);
		}
		list.add(listener);
		Bukkit.getPluginManager().registerEvents(listener, Main.getPlugin());
	}
	
	public static void unloadListener(Listener listener, Module module) {
		List<Listener> list = listeners.get(module);
		if(list == null)
			return;
		
		list.remove(listener);
		HandlerList.unregisterAll(listener);
	}
	
	public static void unloadListeners(Module module) {
		List<Listener> list = listeners.remove(module);
		if(list == null)
			return;
		
		for(Listener listener : list) {
			HandlerList.unregisterAll(listener);
		}
	}
}
