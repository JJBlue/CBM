package essentials.language;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.MainConfig;
import jdk.internal.org.jline.utils.InputStreamReader;

public class LanguageConfig {
	private LanguageConfig() {}
	
	private static File file;
	private static FileConfiguration configuration;
	
	static {
		File f = new File(MainConfig.getDataFolder(), "language/example.yml");
		InputStream inputStream = LanguageConfig.class.getResourceAsStream("en.yml");
		
		try {
			OutputStream outputStream = new FileOutputStream(f);
			inputStream.transferTo(outputStream);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {}
		}
	}
	
	public static void load() {		
		File l = new File(MainConfig.getDataFolder(), "language/" + getLanguage() + ".yml");
		if(!l.exists()) {
			configuration = YamlConfiguration.loadConfiguration(new InputStreamReader(LanguageConfig.class.getResourceAsStream("en.yml")));
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
			File f = new File(MainConfig.getDataFolder(), "language/example.yml");
			FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(f);
			fileConfiguration.set(key, "§4Message is missing");
			try {
				fileConfiguration.save(f);
			} catch (IOException e) {}
			
			m = "§4Message is missing! ";
			
			for(int i = 1; i <= args.length; i++)
				m += "$" + i + " ";
		}
		
		for(int i = 1; i <= args.length; i++)
			m = m.replace("$" + i, args[i]);
		
		return m;
	}
	
	public static void sendMessage(CommandSender sender, String key, String... args) {
		sender.sendMessage(getString(key, args));
	}
}
