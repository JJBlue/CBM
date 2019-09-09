package essentials.updater;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import com.google.common.io.Files;

import components.classes.Static;
import components.downloader.Downloader;
import essentials.main.Main;

public class SpigotPluginUpdater {
	
	private final int pluginID; //CBM = 70992
	private File lastDownloadedFile;
	
	public SpigotPluginUpdater(int ID) {
		this.pluginID = ID;
	}
	
	public String getVersion() {
		return Main.getPlugin().getDescription().getVersion();
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
		return Static.isHeigherVersion(getVersion(), getOnlineVersion());
	}
	
	public synchronized void download() {
		if(!hasNewerVersion() || lastDownloadedFile.exists()) return;
		
		try {
			lastDownloadedFile = new File(ServerManager.getDownloadFolder(), Main.getPlugin().getName() +".jar");
			
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
		if(!lastDownloadedFile.exists()) return;
		
		try {
			Files.move(lastDownloadedFile, Main.getPlugin().getDataFolder().getParentFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}