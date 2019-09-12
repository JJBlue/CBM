package essentials.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.Files;

public class ConfigHelper {
	private ConfigHelper() {}
	
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
	
	public static FileConfiguration loadUTF8(File file) throws InvalidConfigurationException, IOException {
		return YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
	}
	
	public static FileConfiguration loadConfig(File file, String charsetName) throws InvalidConfigurationException, IOException {
		return YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file), Charset.forName(charsetName)));
	}
	
	public static void extractDefaultConfigs(String name, String to) {
		extractFile(ConfigHelper.class.getResourceAsStream("/defaultConfigs/" + name + ".yml"), new File(MainConfig.getDataFolder(), to));
	}
	
	public static void extractDefaultConfigs(String name, File to) {
		extractFile(ConfigHelper.class.getResourceAsStream("/defaultConfigs/" + name + ".yml"), to);
	}
	
	public static void extractFile(InputStream inputStream, File dest) {
		dest.getParentFile().mkdirs();
		
		FileOutputStream outputStream = null;
		
		try {
			outputStream = new FileOutputStream(dest);
			
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
}
