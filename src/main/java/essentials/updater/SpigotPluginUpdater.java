package essentials.updater;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.google.common.io.Files;

import components.classes.Static;
import components.downloader.Downloader;
import essentials.main.Main;

public class SpigotPluginUpdater {
	
	private final int pluginID; //CBM = 70992
	private String name;
	private File lastDownloadedFile;
	
	public SpigotPluginUpdater(int ID) {
		this.pluginID = ID;
	}
	
	public SpigotPluginUpdater(int ID, String name) {
		this.pluginID = ID;
		this.name = name;
	}
	
	public String getVersion() {
		if(name == null)
			return null;
		
		Plugin plugin = Bukkit.getPluginManager().getPlugin(name);
		if(plugin == null)
			return null;
		return plugin.getDescription().getVersion();
	}
	
	public int getPluginID() {
		return pluginID;
	}
	
	public String getOnlineVersion() {
		Scanner scanner = null;
		URLConnection connection = null;
		
		try {
			URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + getPluginID());
			connection = url.openConnection();
			scanner = new Scanner(connection.getInputStream());
			
			if(scanner.hasNextLine())
				return scanner.nextLine();
			return null;
		} catch (IOException e) {
		} finally {
			if(scanner != null)
				scanner.close();
			
			if(connection != null) {
				try {
					if(connection.getInputStream() != null)
						connection.getInputStream().close();
				} catch (IOException e) {}
				
				try {
					if(connection.getOutputStream() != null)
						connection.getOutputStream().close();
				} catch (IOException e) {}
			}
		}
		
		return null;
	}
	
	public boolean hasNewerVersion() {
		String version = getVersion();
		String onlineVersion = getOnlineVersion();
		
		if(version == null || onlineVersion == null) return false;
		
		version = version.toLowerCase();
		onlineVersion = onlineVersion.toLowerCase();
		
		return Static.isHeigherVersion(getVersion(), getOnlineVersion());
	}
	
	/**
	 * You need a name to download a file.
	 */
	public synchronized void download() {
		if(!hasNewerVersion() || (lastDownloadedFile != null && lastDownloadedFile.exists())) return;
		if(name == null) return;
		
		try {
			UpdaterServerManager.getDownloadFolder().mkdirs();
			lastDownloadedFile = new File(UpdaterServerManager.getDownloadFolder(), name +".jar");
			
			Downloader.downloadFile(
				"https://api.spiget.org/v2/resources/" + getPluginID() + "/download",
				lastDownloadedFile,
				-1,
				null
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * WARNING! Maybe you need to restart the Server
	 */
	public synchronized void install() {
		if(lastDownloadedFile == null || !lastDownloadedFile.exists()) return;
		
		try {
			Files.move(lastDownloadedFile, new File(Main.getPlugin().getDataFolder().getParentFile() + "/" + lastDownloadedFile.getName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
