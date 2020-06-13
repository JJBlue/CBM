package cbm.modules.world;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import cbm.config.ConfigHelper;
import cbm.config.MainConfig;
import cbm.modules.world.time.TimeWorldConfig;

import java.io.File;
import java.io.IOException;

public class WorldConfig {

	private WorldConfig() {}

	static File file;
	static FileConfiguration configuration;

	public static void load() {
		file = new File(MainConfig.getDataFolder(), "worlds.yml");

		if (!file.exists())
			ConfigHelper.extractDefaultConfigs("worlds", "worlds.yml");

		configuration = YamlConfiguration.loadConfiguration(file);

		TimeWorldConfig.load();
	}

	public static void unload() {
		TimeWorldConfig.unload();

		configuration = null;
		file = null;
	}

	public static File getFile() {
		return file;
	}

	public static FileConfiguration getConfiguration() {
		return configuration;
	}

	public static ConfigurationSection getConfigurationSection(String name) {
		return configuration.getConfigurationSection(name);
	}

	public static void save() {
		try {
			configuration.save(file);
		} catch (IOException e) {}
	}
}
