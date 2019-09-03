package essentials.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.Files;

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
