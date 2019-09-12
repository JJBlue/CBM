package essentials.config;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import essentials.main.Main;

public class MainConfig {
	private MainConfig() {}
	
	private static File configFile;
	private static FileConfiguration configuration;
	
	private static boolean isFirstTime;
	private static String lastVerstionRun;
	private static boolean useBStats;
	
	public static void reload() {
		configFile = new File(Main.getPlugin().getDataFolder(), "config.yml");
		isFirstTime = !configFile.exists();
		
		if(isFirstTime)
			ConfigHelper.extractDefaultConfigs("config", configFile);
		
		try {
			configuration = ConfigHelper.loadUTF8(configFile);
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
		
		//Plugin
		configuration.addDefault(MainConfigEnum.DataFolder.value, "-");
		configuration.addDefault(MainConfigEnum.Language.value, "en");
		lastVerstionRun = configuration.getString(MainConfigEnum.lastVersionRun.value);
		configuration.set(MainConfigEnum.lastVersionRun.value, Main.getPlugin().getDescription().getVersion());
		
		//Server
		configuration.addDefault(MainConfigEnum.FullSize.value, -1);
		configuration.addDefault(MainConfigEnum.FullMessage.value, "§4Der Server ist voll");
		
		List<String> list = new LinkedList<>();
		list.add("#UUID (could join when onlinePlayers < showPlayerAmount)");
		configuration.addDefault(MainConfigEnum.JoinPlayersWhenFull.value, list);
		
		list = new LinkedList<>();
		list.add("#UUID");
		configuration.addDefault(MainConfigEnum.CouldOperators.value, list);
		
		configuration.addDefault(MainConfigEnum.Motd.value, "§4Error 404 Message is missing");
		
		List<String> stringsTmp = new LinkedList<>();
		stringsTmp.add("stop");
		configuration.addDefault(MainConfigEnum.Restart.value, stringsTmp);
		
		//bstats
		useBStats = configuration.getBoolean(MainConfigEnum.bStatsEnable.value);
		configuration.addDefault(MainConfigEnum.bStatsEnable.value, true);
		
		configuration.options().copyDefaults(true);
		save();
	}
	
	public static String getDataFolder() {
		String folder = configuration.getString(MainConfigEnum.DataFolder.value);

		if(folder == null || folder.isEmpty() || folder.equals("-"))
			folder = Main.getPlugin().getDataFolder().getAbsolutePath();
		
		if(!folder.endsWith("\\") && !folder.endsWith("/"))
			return folder + "/";
		return folder;
	}
	
	public static void setDataFolder(String folder) {
		configuration.set(MainConfigEnum.DataFolder.value, folder);
		save();
	}
	
	public static int getFullSize() {
		return configuration.getInt(MainConfigEnum.FullSize.value);
	}
	
	public static void setFullSize(int size) {
		configuration.set(MainConfigEnum.FullSize.value, size);
		save();
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getJoinable() {
		return (List<String>) configuration.getList(MainConfigEnum.JoinPlayersWhenFull.value);
	}
	
	public static void setJoinable(List<String> list) {
		configuration.set(MainConfigEnum.JoinPlayersWhenFull.value, list);
		save();
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getOperators() {
		return (List<String>) configuration.getList(MainConfigEnum.CouldOperators.value);
	}
	
	public static String getFullMessage() {
		return configuration.getString(MainConfigEnum.FullMessage.value);
	}
	
	public static void setFullMessage(String message) {
		configuration.set(MainConfigEnum.FullMessage.value, message);
		save();
	}
	
	public static String getMotd() {
		return configuration.getString(MainConfigEnum.Motd.value);
	}
	
	public static void setMotd(String message) {
		configuration.set(MainConfigEnum.Motd.value, message);
		save();
	}
	
	public static String getLanguage() {
		return configuration.getString(MainConfigEnum.Language.value);
	}
	
	public static void setLanguage(String lang) {
		configuration.set(MainConfigEnum.Language.value, lang);
		save();
	}
	
	public static void save() {
		try {
			configuration.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFirstTime() {
		return isFirstTime;
	}

	public static FileConfiguration getConfiguration() {
		return configuration;
	}

	public static String getLastVerstionRun() {
		return lastVerstionRun;
	}

	public static boolean useBStats() {
		return useBStats;
	}

	public static void restart() {
		//TODO
		List<String> restartList = configuration.getStringList(MainConfigEnum.Restart.value);
		
		for(String element : restartList) {
			if(element.startsWith("!file")) {
				if(element.length() <= 6) continue;
				element = element.substring(6, element.length());
				
				File file = new File(element);
				if(!file.exists() || file.isDirectory()) continue;
				
				try {
					Runtime.getRuntime().exec(file.getAbsolutePath(), null, file.getParentFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), element);
		}
	}
}
