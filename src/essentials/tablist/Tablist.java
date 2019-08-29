package essentials.tablist;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import essentials.config.MainConfig;
import essentials.main.Main;
import essentials.permissions.PermissionHelper;
import essentials.utilities.TablistUtilities;

public class Tablist {
	private Tablist() {}
	
	static File file;
	static FileConfiguration configuration;
	
	static boolean enabled;
	static boolean groupEnabled;
	static final String PREFIX = "TabList.";
	static boolean defaultEnabled;
	static TablistListener tablistListener;
	
	static {
		tablistListener = new TablistListener();
	}
	
	public synchronized static void load() {
		file = new File(MainConfig.getDataFolder(), "tablist");
		configuration = YamlConfiguration.loadConfiguration(file);
		
		//TODO
		configuration.addDefault(PREFIX + "Enabled", true); //TODO set to false
		configuration.addDefault(PREFIX + "DefaultEnabled", true);
		configuration.addDefault(PREFIX + "GroupEnabled", false);
		
		configuration.addDefault(PREFIX + "Udates.AutoInterval", 60);
		configuration.addDefault(PREFIX + "Udates.onAfk", false);
		
		if(!file.exists()) {
			List<String> headerFooter = new LinkedList<>();
			headerFooter.add("Default text");
			configuration.addDefault(PREFIX + "DefaultTablist.Header", headerFooter);
			configuration.addDefault(PREFIX + "DefaultTablist.Footer", headerFooter);
			
			configuration.addDefault(PREFIX + "GroupTablist.1.Header", headerFooter);
			configuration.addDefault(PREFIX + "GroupTablist.1.Footer", headerFooter);
		}
		
		configuration.options().copyDefaults(true);
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Loading
		enabled = configuration.getBoolean(PREFIX + "Enabled");
		defaultEnabled = configuration.getBoolean(PREFIX + "DefaultEnabled");
		groupEnabled = configuration.getBoolean(PREFIX + "GroupEnabled");
		TablistTimer.setSleep(configuration.getInt(PREFIX + "Udates.AutoInterval") * 1000);
		
		Bukkit.getPluginManager().registerEvents(tablistListener, Main.getPlugin());
		
		if(enabled)
			TablistTimer.start();
	}
	
	public synchronized static void unload() {
		TablistTimer.stop();
		HandlerList.unregisterAll(tablistListener);
	}
	
	public static void updateAllPlayers() {
		for(Player player : Bukkit.getOnlinePlayers())
			update(player);
	}
	
	public static void update(Player player) {
		if(!enabled) return;
		if(!groupEnabled && !defaultEnabled) return;
		
		if(!groupEnabled) {
			sendTablist(player, -1);
			return;
		}
		
		List<String> foundPermissions = new LinkedList<>();
		
		String prefix = PermissionHelper.getPermission("tablist");
		player.getEffectivePermissions().forEach(pai -> {
			if(pai.getPermission().startsWith(prefix))
				foundPermissions.add(pai.getPermission());
		});
		
		int biggestNumber = -1;
		
		for(String permission : foundPermissions) {
			try {
				int number = Integer.parseInt(permission.substring(prefix.length(), permission.length()));
				if(number > biggestNumber)
					biggestNumber = number;
			} catch (NumberFormatException e) {}
		}
		
		sendTablist(player, biggestNumber);
	}
	
	/**
	 * 
	 * @param player
	 * @param number of the Group Tablist, -1 is default
	 */
	public static void sendTablist(Player player, int number) {
		String header;
		String footer;
		
		if(number == -1) {
			header = configuration.getString(PREFIX + "DefaultTablist.Header");
			footer = configuration.getString(PREFIX + "DefaultTablist.Footer");
			
			TablistUtilities.sendHeaderFooter(player, header, footer);
			return;
		}
		
		header = configuration.getString(PREFIX + "GroupTablist." + number + ".Header");
		footer = configuration.getString(PREFIX + "GroupTablist." + number + ".Header");
		TablistUtilities.sendHeaderFooter(player, header, footer);
	}
}
