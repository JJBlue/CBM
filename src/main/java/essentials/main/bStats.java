package essentials.main;

import org.bstats.bukkit.Metrics;

import essentials.language.LanguageConfig;

public class bStats {
	public static void enableBStats() {
		Metrics metrics = new Metrics(Main.getPlugin());
		metrics.addCustomChart(new Metrics.SimplePie("language", () -> LanguageConfig.getLanguage()));
	}
}
