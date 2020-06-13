package cbm.modules.updater;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cbm.config.MainConfig;
import cbm.language.LanguageConfig;
import cbm.utilities.permissions.PermissionHelper;
import cbm.utilitiesvr.bukkit.BukkitUtilities;
import components.classes.Files;

public class UpdaterServerManager {
	private UpdaterServerManager() {}

	static Map<Integer, SpigotPluginUpdater> plugins = Collections.synchronizedMap(new HashMap<>());
	static File downloadFolder;

	public static void load() {
		UpdaterConfig.load();
	}

	public static void unload() {
		UpdaterConfig.unload();
		clearPlugins();
	}

	public static void addPlugin(SpigotPluginUpdater updater) {
		plugins.put(updater.getPluginID(), updater);
	}

	public static void removePlugin(int ID) {
		plugins.remove(ID);
	}

	public static boolean containsID(int ID) {
		return plugins.containsKey(ID);
	}

	public static void clearPlugins() {
		plugins.clear();
	}

	public static void restart() {
		MainConfig.restart();
	}

	public static void updateInstall() {
		update();
		install();
	}

	public static List<String> checkForUpdate() {
		List<String> list = new LinkedList<>();

		synchronized (plugins) {
			for (SpigotPluginUpdater plugin : plugins.values())
				if (plugin.hasNewerVersion()) {
					if (plugin.getName() == null && plugin.getName().isEmpty())
						list.add(plugin.getPluginID() + " (ID)");
					else
						list.add(plugin.getName());
				}
		}

		return list;
	}

	public static void update() {
		synchronized (plugins) {
			for (SpigotPluginUpdater plugin : plugins.values()) {
				if(plugin.download())
					BukkitUtilities.broadcast(LanguageConfig.getString("updater.download-plugin", plugin.getName()), PermissionHelper.getPermission("updater.seeBroadcast"));
			}
		}

		BukkitUtilities.broadcast(LanguageConfig.getString("updater.download-complete"), PermissionHelper.getPermission("updater.seeBroadcast"));
	}

	public static void install() {
		synchronized (plugins) {
			for (SpigotPluginUpdater plugin : plugins.values()) {
				if(plugin.install())
					BukkitUtilities.broadcast(LanguageConfig.getString("updater.install-plugin", plugin.getName()), PermissionHelper.getPermission("updater.seeBroadcast"));
			}
		}

		BukkitUtilities.broadcast(LanguageConfig.getString("updater.install-complete"), PermissionHelper.getPermission("updater.seeBroadcast"));

		restart();
	}

	public static void cleanUpFolder() {
		Files.delete(downloadFolder);
	}

	public static File getDownloadFolder() {
		return downloadFolder;
	}

	public static void setDownloadFolder(File downloadFolder) {
		UpdaterServerManager.downloadFolder = downloadFolder;
	}
}
