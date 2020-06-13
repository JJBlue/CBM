package cbm.modules.updater;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import cbm.config.ConfigHelper;
import cbm.config.MainConfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UpdaterConfig {
	static File configFile;
	static FileConfiguration configuration;

	public static void load() {
		configFile = new File(MainConfig.getDataFolder(), "Updater.yml");

		if (!configFile.exists())
			ConfigHelper.extractDefaultConfigs("Updater", "Updater.yml");

		configuration = YamlConfiguration.loadConfiguration(configFile);

		//Load default
		{
			configuration.addDefault("install_on_shutdown", false);
			configuration.addDefault("downloadFolder", "-");

			/*
			 * 		UpdatePlugins:
			 * 		- ID: <ID>
			 * 		  name: <Name>
			 * 		  URL: (<URL>)		//TODO Not work today
			 * 		- ID: <ID>
			 * 		  ...
			 *
			 */
			List<Map<?, ?>> pluginsDefault = new LinkedList<>();

			Map<String, Object> cbm = new HashMap<>();
			cbm.put("ID", 70992);
			cbm.put("name", "CBM");

			pluginsDefault.add(cbm);
			configuration.addDefault("UpdatePlugins", pluginsDefault);

			configuration.options().copyDefaults(true);
			save();

			String downloadFolder = configuration.getString("downloadFolder");
			if (downloadFolder == null || downloadFolder.isEmpty() || downloadFolder.equalsIgnoreCase("-"))
				downloadFolder = MainConfig.getDataFolder() + "/download";

			UpdaterServerManager.downloadFolder = new File(downloadFolder);
			UpdaterServerManager.downloadFolder.mkdirs();
		}

		//Load Plugins
		{
			List<?> pluginsMap = configuration.getList("UpdatePlugins");
			if (pluginsMap != null) {
				for (Object obj : pluginsMap) {
					if (!(obj instanceof Map)) continue;

					Map<?, ?> map = (Map<?, ?>) obj;

					SpigotPluginUpdater spigotPluginUpdater = new SpigotPluginUpdater((int) map.get("ID"), (String) map.get("name"));
					UpdaterServerManager.addPlugin(spigotPluginUpdater);
				}
			}
		}
	}

	public static void unload() {
		save();
		configFile = null;
		configuration = null;
	}

	public static boolean isInstallOnShutdown() {
		if (configuration == null) return false;
		return configuration.getBoolean("install_on_shutdown");
	}

	private static void save() {
		try {
			configuration.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
