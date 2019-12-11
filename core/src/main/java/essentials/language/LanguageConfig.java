package essentials.language;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.config.ConfigHelper;
import essentials.config.MainConfig;
import essentials.utilities.BukkitUtilities;
import essentials.utilities.StringUtilities;

public class LanguageConfig {
	private LanguageConfig() {}

	private static File file;
	private static FileConfiguration configuration;

	private static FileConfiguration fallbackOne; //Fallback of the same language
	private static FileConfiguration fallback;

	static {
		ConfigHelper.extractFile(LanguageConfig.class.getResourceAsStream("en.yml"), new File(MainConfig.getDataFolder(), "language/example.yml"));
	}

	public static void load() {
		File l = new File(MainConfig.getDataFolder(), "language/" + getLanguage() + ".yml");
		if (l.exists()) {
			try {
				configuration = ConfigHelper.loadConfig(l, "UTF-8");
				file = l;
			} catch (InvalidConfigurationException | IOException e) {
				e.printStackTrace();
			}
			
			//fallback for specific language
			try {
				fallbackOne = ConfigHelper.loadConfig(LanguageConfig.class.getResourceAsStream(getLanguage() + ".yml"), "UTF-8");
			} catch (InvalidConfigurationException | IOException e1) {
				fallbackOne = null;
			}
			
			return;
		} else {
			try {
				configuration = ConfigHelper.loadConfig(LanguageConfig.class.getResourceAsStream(getLanguage() + ".yml"), "UTF-8");
			} catch (InvalidConfigurationException | IOException e1) {
				configuration = null;
			}
		}

		// No fallback to specific lanuage? When use en.yml
		try {
			fallback = ConfigHelper.loadConfig(LanguageConfig.class.getResourceAsStream("en.yml"), "UTF-8");
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void save() {
		if (file == null || configuration == null) return;
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
		if (configuration != null) {
			String r = configuration.getString(key);
			if (r != null)
				return r;
		}
		if(fallbackOne != null) {
			String r = fallbackOne.getString(key);
			if(r != null)
				return r;
		}
		return fallback.getString(key);
	}

	public static String getString(String key, String... args) {
		String m = getString(key);

		if (m == null) {
			addMissingStringToExample(key);

			m = "§4Message is missing! Paramters: ";

			for (int i = 1; i <= args.length; i++)
				m += "$" + i + " ";
		}

		m = StringUtilities.setArgs(m, args);

		m = m.replaceAll("(?<!\\\\)\\\\n", "\n");

		return m.toString();
	}

	private static void addMissingStringToExample(String key) {
		try {
			File f = new File(MainConfig.getDataFolder(), "language/example.yml");
			FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(f);
			fileConfiguration.set(key, "§4Message is missing Paramters: ");
			fileConfiguration.save(f);
		} catch (Exception ignored) {}
	}

	public static void sendMessage(CommandSender sender, String key, String... args) {
		sender.sendMessage(getString(key, args));
	}
	
	public static void sendMessageWithPermission(String permission, String key, String... args) {
		BukkitUtilities.broadcast(getString(key, args), permission);
	}
}