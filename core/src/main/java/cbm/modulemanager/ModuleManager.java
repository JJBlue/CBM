package cbm.modulemanager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import cbm.main.Main;
import cbm.modules.FlyThroughBlocks.FTBModule;
import cbm.modules.MapPaint.MapPaintModule;
import cbm.modules.alias.CustomAliasModule;
import cbm.modules.armorstandeditor.ArmorstandModule;
import cbm.modules.ban.BanModule;
import cbm.modules.commandonitemstack.CoIModule;
import cbm.modules.commandonobject.CoBModule;
import cbm.modules.commandspy.CommandSpyModule;
import cbm.modules.costumerecipes.CustomRecipeModule;
import cbm.modules.debugstick.DebugStickModule;
import cbm.modules.display.DisplayModule;
import cbm.modules.operator.OpModule;
import cbm.modules.player.MidiPlayerModule;
import cbm.modules.spawn.SpawnModule;
import cbm.modules.sudo.SudoModule;
import cbm.modules.tablist.TablistModule;
import cbm.modules.timer.TimerModule;
import cbm.modules.trade.TradeModule;
import cbm.modules.troll.TrollModule;
import cbm.modules.updater.UpdaterModule;
import cbm.modules.warpmanager.WarpModule;

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
		
		ModuleConfig.save();
	}
	
	public static void addDefaultModules() {
		addModule(new ArmorstandModule());
		addModule(new BanModule());
		addModule(new MidiPlayerModule());
		addModule(new CoBModule());
		addModule(new CoIModule());
		addModule(new CommandSpyModule());
		addModule(new CustomAliasModule());
		addModule(new CustomRecipeModule());
		addModule(new DebugStickModule());
		addModule(new DisplayModule());
		addModule(new FTBModule());
//		addModule(new KitsModule());
		addModule(new MapPaintModule());
		addModule(new OpModule());
		addModule(new SpawnModule());
		addModule(new SudoModule());
		addModule(new TablistModule());
		addModule(new TimerModule());
		addModule(new TradeModule());
		addModule(new TrollModule());
		addModule(new UpdaterModule());
		addModule(new WarpModule());
		
//		addModule(new ChatVerbesserungModule());
	}
	
	public static void unload() {
		if(modules == null) return;
		
		synchronized (modules) {
			for(Module module : modules.values()) {
				if(module.isLoaded()) {
					Main.unloadHelper(() -> disable(module));
				}
			}
		}
		
		modules = null;
		listeners = null;
	}
	
	public static void enable(Module module) {
		if(module == null || module.isLoaded()) return;
		module.enable();
	}
	
	public static void disable(Module module) {
		if(module == null || !module.isLoaded()) return;
		module.disable();
		unloadListeners(module);
	}
	
	public static void addModule(Module module) {
		if(module == null) return;
		
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

	public static Collection<Module> getModules() {
		return modules.values();
	}
}
