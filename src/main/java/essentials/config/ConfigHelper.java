package essentials.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ConfigHelper {
	private ConfigHelper() {}

	public static FileConfiguration loadConfig(InputStream inputStream, String charsetName) throws InvalidConfigurationException, IOException {
		if(inputStream == null || charsetName == null) return null;
		return loadConfig(new InputStreamReader(inputStream, Charset.forName(charsetName)));
	}

	public static FileConfiguration loadUTF8(File file) throws InvalidConfigurationException, IOException {
		if(file == null) return null;
		return loadConfig(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
	}

	public static FileConfiguration loadConfig(File file, String charsetName) throws InvalidConfigurationException, IOException {
		if(file == null || charsetName == null) return null;
		return loadConfig(new InputStreamReader(new FileInputStream(file), Charset.forName(charsetName)));
	}
	
	public static FileConfiguration loadConfig(InputStream inputStream) {
		if(inputStream == null) return null;
		try {
			if(inputStream.available() == 0) return null;
		} catch (IOException e) {
			return null;
		}
		return YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
	}
	
	public static FileConfiguration loadConfig(InputStreamReader reader) {
		if(reader == null) return null;
		try {
			if(!reader.ready()) return null;
		} catch (IOException e) {
			return null;
		}
		return YamlConfiguration.loadConfiguration(reader);
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

			while (inputStream.available() > 0)
				outputStream.write(inputStream.read());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException ignored) {}

			try {
				if (outputStream != null)
					outputStream.close();
			} catch (Exception ignored) {}
		}
	}
}
