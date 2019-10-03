package essentials.main;

import essentials.language.LanguageConfig;
import org.bstats.bukkit.Metrics;

public class bStats {
	public static void enableBStats() {
		Metrics metrics = new Metrics(Main.getPlugin());
		metrics.addCustomChart(new Metrics.SimplePie("language", LanguageConfig::getLanguage));
	}
}
