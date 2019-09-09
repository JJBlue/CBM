package essentials.updater;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import components.classes.Files;

public class ServerManager {
	private ServerManager() {}
	
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
		//TODO
	}
	
	public static void updateInstallAndRestart() {
		update();
		install();
		restart();
	}
	
	public static List<String> checkForUpdate() {
		List<String> list = new LinkedList<>();
		
		synchronized (plugins) {
			for(SpigotPluginUpdater plugin : plugins.values())
				if(plugin.hasNewerVersion()) {
					if(plugin.getName() == null && plugin.getName().isEmpty())
						list.add(plugin.getPluginID() + " (ID)");
					else
						list.add(plugin.getName());
				}
		}
		
		return list;
	}
	
	public static void update() {
		synchronized (plugins) {
			for(SpigotPluginUpdater plugin : plugins.values())
				plugin.download();
		}
	}
	
	public static void install() {
		synchronized (plugins) {
			for(SpigotPluginUpdater plugin : plugins.values())
				plugin.install();
		}
	}
	
	public static void cleanUpFolder() {
		Files.delete(downloadFolder);
	}
	
	public static File getDownloadFolder() {
		return downloadFolder;
	}

	public static void setDownloadFolder(File downloadFolder) {
		ServerManager.downloadFolder = downloadFolder;
	}
}
