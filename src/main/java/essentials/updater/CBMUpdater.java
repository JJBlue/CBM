package essentials.updater;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import components.classes.Static;
import components.downloader.Downloader;
import essentials.main.Main;

public class CBMUpdater {
	public static String getVersion() {
		return Main.getPlugin().getDescription().getVersion();
	}
	
	public static int getPluginID() {
		return 70992;
	}
	
	public static String getOnlineVersion() {
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
	
	public static boolean hasNewerVersion() {
		return Static.isHeigherVersion(getVersion(), getOnlineVersion());
	}
	
	/*
	 * WARNING! Maybe you must restart your Server.
	 */
	public static void download() {
		try {
			Downloader.downloadFile("https://api.spiget.org/v2/resources/" + getPluginID() + "/download", new File(Main.getPlugin().getDataFolder().getParentFile(), Main.getPlugin().getName() +".jar"), -1, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
