package essentials.config;

import com.google.common.io.Files;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.Charset;

public class ConfigLoader {
	private ConfigLoader() {}
	
	public static FileConfiguration loadConfig(InputStream inputstream) {
		return YamlConfiguration.loadConfiguration(new InputStreamReader(inputstream));
	}
	
	public static FileConfiguration loadConfigFileToString(File file, String charsetName) throws InvalidConfigurationException, IOException {
		FileConfiguration fileConfiguration = new YamlConfiguration();
		fileConfiguration.loadFromString(Files.toString(file, Charset.forName(charsetName)));
		return fileConfiguration;
	}
	
	public static FileConfiguration loadConfig(InputStream inputStream, String charsetName) throws InvalidConfigurationException, IOException {
		return YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream, Charset.forName(charsetName)));
	}
	
	public static FileConfiguration loadConfig(File file, String charsetName) throws InvalidConfigurationException, IOException {
		return YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file), Charset.forName(charsetName)));
	}
}
