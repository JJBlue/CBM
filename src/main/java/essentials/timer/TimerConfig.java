package essentials.timer;

import essentials.config.MainConfig;
import essentials.language.LanguageConfig;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TimerConfig {
	private TimerConfig() {}
	
	private static File file;
	private static FileConfiguration configuration;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder(), "timer.yml");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write("#Name of the timer. This Timer is called 'default'. You need for each Timer: position, title, (color), maxValue, countUpOrDown");
				writer.write("\ndefault:");
				writer.write("\n  #Position of the Timer. Values are BOSSBAR, CHAT, NOWHERE");
				writer.write("\n  position: BOSSBAR");
				writer.write("\n  #Only used for Bossbar. Possible Colors are: BLUE, GREEN, PINK, PURPLE, RED, WHITE, YELLOW");
				writer.write("\n  color: BLUE");
				writer.write("\n  #Title of Bossbar or Chat Message");
				writer.write("\n  title: Counddown $1");
				writer.write("\n  maxValue: 60");
				writer.write("\n  #Count Up or Down. Values are: UP, DOWN");
				writer.write("\n  countUpOrDown: DOWN");
				writer.write("\n  finished:");
				writer.write("\n  - say Counddown completed");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		configuration = YamlConfiguration.loadConfiguration(file);
	}
	
	public static BukkitTimer startTimer(String timer) {
		if(!configuration.contains(timer)) return null;
		
		try {
			BukkitTimer bukkitTimer = new BukkitTimer(TimerPosition.valueOf(configuration.getString(timer + ".position").toUpperCase()));
			bukkitTimer.setTitle(configuration.getString(timer + ".title"));
			bukkitTimer.setMaxValue(configuration.getInt(timer + ".maxValue"));
			
			switch (configuration.getString(timer + ".countUpOrDown").toUpperCase()) {
				case "UP":
						bukkitTimer.setCountUp();
					break;
				case "DOWN":
					bukkitTimer.setCountDown();
					break;
			}
			
			if(configuration.contains(timer + ".color"))
				bukkitTimer.setColor(BarColor.valueOf(configuration.getString(timer + ".color").toUpperCase()));
			
			if(configuration.contains(timer + ".finished")) {
				bukkitTimer.setOnFinished(() -> {
					List<?> list = configuration.getList(timer + ".finished");
					if(list == null) return;
					
					for(Object obj : list) {
						if(obj instanceof String)
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (String) obj);
						else
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), obj.toString());
					}
					
				});
			}
			
			bukkitTimer.start();
			
			return bukkitTimer;
			
		} catch (IllegalArgumentException e) {
			Bukkit.getConsoleSender().sendMessage("§4Error in timer.yml file for " + timer);
			LanguageConfig.sendMessage(Bukkit.getConsoleSender(), "error.IllegalArgumentException");
		}
		
		return null;
	}
}
