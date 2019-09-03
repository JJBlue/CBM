package essentials.timer;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class TimerConfig {
	private TimerConfig() {}
	
	private static File file;
	private static FileConfiguration configuration;
	
	/*
	 * <key>.*
	 * 
	 * *finished: <List of Commands>
	 * *position: <Bossbar, Chat, Nowhere>
	 * *color: <Bossbar Color>
	 * *title: <Bossbar Title>
	 * *message: <Chat message if position = Chat>
	 * *maxValue: <maxValue = how long in sekunds>
	 * *countUpOrDown: <True = Up, False = Down ?>
	 *
	 */
}
