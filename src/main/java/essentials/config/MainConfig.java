package essentials.config;

import essentials.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MainConfig {
	private MainConfig() {}
	
	private static File configFile;
	private static FileConfiguration configuration;
	
	public static void reload() {
		configFile = new File(Main.getPlugin().getDataFolder(), "config.yml");
		configuration = YamlConfiguration.loadConfiguration(configFile);
		configuration.options().copyDefaults(true);
		
		configuration.addDefault(MainConfigEnum.DataFolder.value, "-");
		configuration.addDefault(MainConfigEnum.FullSize.value, -1);
		configuration.addDefault(MainConfigEnum.FullMessage.value, "§4Der Server ist voll");
		
		List<String> list = new LinkedList<>();
		list.add("#UUID (could join when onlinePlayers < showPlayerAmount)");
		configuration.addDefault(MainConfigEnum.JoinPlayersWhenFull.value, list);
		
		list = new LinkedList<>();
		list.add("#UUID");
		configuration.addDefault(MainConfigEnum.CouldOperators.value, list);
		
		configuration.addDefault(MainConfigEnum.Motd.value, "§4Error 404 Message is missing");
		
		//TODO remove later
		String folder = configuration.getString(MainConfigEnum.DataFolder.value);
		if(new File(folder).equals(new File("./plugins/Allgemein/")))
			configuration.set(MainConfigEnum.DataFolder.value, "-");
		
		configuration.addDefault(MainConfigEnum.Language.value, "en");
		
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
}