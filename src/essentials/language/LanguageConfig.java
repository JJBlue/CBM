package essentials.language;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.ConfigLoader;
import essentials.config.MainConfig;

public class LanguageConfig {
	private LanguageConfig() {}
	
	private static File file;
	private static FileConfiguration configuration;
	
	static {
		File f = new File(MainConfig.getDataFolder(), "language/example.yml");
		f.getParentFile().mkdirs();
		
		InputStream inputStream = LanguageConfig.class.getResourceAsStream("en.yml");
		FileOutputStream outputStream = null;
		
		try {
			outputStream = new FileOutputStream(f);
			while(inputStream.available() > 0)
				outputStream.write(inputStream.read());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {}
			
			try {
				if(outputStream != null)
					outputStream.close();
			} catch (Exception e2) {}
		}
	}
	
	public static void load() {		
		File l = new File(MainConfig.getDataFolder(), "language/" + getLanguage() + ".yml");
		if(!l.exists()) {
			configuration = ConfigLoader.loadConfig(LanguageConfig.class.getResourceAsStream("en.yml"));
			return;
		}
		
		file = l;
		configuration = YamlConfiguration.loadConfiguration(l);
	}
	
	public static void save() {
		if(file == null || configuration == null) return;
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setLanguage(String lang) {
		MainConfig.setLanguage(lang);
	}
	
	public static String getLanguage() {
		return MainConfig.getLanguage();
	}
	
	public static String getString(String key) {
		return configuration.getString(key);
	}
	
	public static String getString(String key, String... args) {
		String m = getString(key);
		
		if(m == null) {
			addMissingStringToExample(key);
			
			m = "§4Message is missing! Paramters: ";
			
			for(int i = 1; i <= args.length; i++)
				m += "$" + i + " ";
		}
		
		for(int i = 1; i <= args.length; i++)
			m = m.replace("$" + i, args[i - 1]);
		
		return m;
	}
	
	private static void addMissingStringToExample(String key) {
		try {
			File f = new File(MainConfig.getDataFolder(), "language/example.yml");
			FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(f);
			fileConfiguration.set(key, "§4Message is missing Paramters: ");
			fileConfiguration.save(f);
		} catch (Exception e) {}
	}
	
	public static void sendMessage(CommandSender sender, String key, String... args) {
		sender.sendMessage(getString(key, args));
	}
}
