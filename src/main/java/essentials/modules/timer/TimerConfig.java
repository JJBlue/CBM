package essentials.modules.timer;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.ConfigHelper;
import essentials.config.MainConfig;
import essentials.language.LanguageConfig;

public class TimerConfig {
	private TimerConfig() {}

	private static File file;
	private static FileConfiguration configuration;

	public static void load() {
		file = new File(MainConfig.getDataFolder(), "timer.yml");

		if (!file.exists())
			ConfigHelper.extractDefaultConfigs("timer", "timer.yml");

		configuration = YamlConfiguration.loadConfiguration(file);
	}

	public static void unload() {
		configuration = null;
		file = null;
	}
	
	public static BukkitTimer startTimer(String timer) {
		if (!configuration.contains(timer)) return null;

		try {
			BukkitTimer bukkitTimer = new BukkitTimer(TimerPosition.valueOf(configuration.getString(timer + ".position").toUpperCase()));
			bukkitTimer.setTitle(configuration.getString(timer + ".title"));
			bukkitTimer.setMaxValue(configuration.getInt(timer + ".maxValue"));

			switch (configuration.getString(timer + ".countUpOrDown").toUpperCase()) {
				case "UP":
					bukkitTimer.setCountUp();
					break;
				case "DOWN":
					bukkitTimer.setCountDown();
					break;
			}

			if (configuration.contains(timer + ".color"))
				bukkitTimer.setColor(BarColor.valueOf(configuration.getString(timer + ".color").toUpperCase()));

			if (configuration.contains(timer + ".finished")) {
				bukkitTimer.setOnFinished(() -> {
					List<?> list = configuration.getList(timer + ".finished");
					if (list == null) return;

					for (Object obj : list) {
						if (obj instanceof String)
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (String) obj);
						else
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), obj.toString());
					}

				});
			}

			bukkitTimer.start();

			return bukkitTimer;

		} catch (IllegalArgumentException e) {
			Bukkit.getConsoleSender().sendMessage("§4Error in timer.yml file for " + timer);
			LanguageConfig.sendMessage(Bukkit.getConsoleSender(), "error.IllegalArgumentException");
		}

		return null;
	}
}
