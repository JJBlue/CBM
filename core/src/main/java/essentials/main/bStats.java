package essentials.main;

import java.util.HashMap;
import java.util.Map;

import org.bstats.bukkit.Metrics;

import essentials.language.LanguageConfig;
import essentials.modulemanager.ModuleManager;
import essentials.modulemanager.Module;

public class bStats {
	public static void enableBStats() {
		Metrics metrics = new Metrics(Main.getPlugin());
		
		metrics.addCustomChart(new Metrics.SimplePie("language", LanguageConfig::getLanguage));
		
//		metrics.addCustomChart(new Metrics.SimpleBarChart("modules", () -> {
//			Map<String, Integer> maps = new HashMap<>();
//			
//			for(Module module : ModuleManager.getModules()) {
//				maps.put(module.getID(), 1);
//			}
//			
//			return maps;
//		}));
		
		metrics.addCustomChart(new Metrics.AdvancedPie("modules", () -> {
			Map<String, Integer> maps = new HashMap<>();
			
			for(Module module : ModuleManager.getModules()) {
				maps.put(module.getID(), 1);
			}
			
			return maps;
		}));
	}
}
