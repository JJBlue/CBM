package cbm.main;

import java.util.HashMap;
import java.util.Map;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.AdvancedPie;
import org.bstats.charts.SimplePie;

import cbm.language.LanguageConfig;
import cbm.modulemanager.Module;
import cbm.modulemanager.ModuleManager;

public class bStats {
	public static void enableBStats() {
		Metrics metrics = new Metrics(Main.getPlugin(), 5576);
		
		metrics.addCustomChart(new SimplePie("language", LanguageConfig::getLanguage));
		
//		metrics.addCustomChart(new Metrics.SimpleBarChart("modules", () -> {
//			Map<String, Integer> maps = new HashMap<>();
//			
//			for(Module module : ModuleManager.getModules()) {
//				maps.put(module.getID(), 1);
//			}
//			
//			return maps;
//		}));
		
		metrics.addCustomChart(new AdvancedPie("modules", () -> {
			Map<String, Integer> maps = new HashMap<>();
			
			for(Module module : ModuleManager.getModules()) {
				maps.put(module.getID(), 1);
			}
			
			return maps;
		}));
	}
}
