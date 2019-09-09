package essentials.updater;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ServerManager {
	private ServerManager() {}
	
	private static List<SpigotPluginUpdater> plugins = Collections.synchronizedList(new LinkedList<>());
	private static File downloadFolder;
	
	public static void load() {
		//TODO
	}
	
	public static void unload() {
		//TODO
	}
	
	public static void restart() {
		//TODO
	}
	
	public static void updateInstallAndRestart() {
		update();
		install();
		restart();
	}
	
	public static String checkForUpdate() {
		return null; //TODO
	}
	
	public static void update() {
		synchronized (plugins) {
			for(SpigotPluginUpdater plugin : plugins)
				plugin.download();
		}
	}
	
	public static void install() {
		synchronized (plugins) {
			for(SpigotPluginUpdater plugin : plugins)
				plugin.install();
		}
	}
	
	public static File getDownloadFolder() {
		return downloadFolder;
	}
}
