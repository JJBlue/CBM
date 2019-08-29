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
	
	static boolean onJoin;
	static boolean onDeath;
	static boolean onTeleport;
	static boolean onWorldChange;
	
	static int counterPageCurrent = 0; //TODO
	
	static {
		tablistListener = new TablistListener();
	}
	
	public synchronized static void load() {
		file = new File(MainConfig.getDataFolder(), "tablist.yml");
		configuration = YamlConfiguration.loadConfiguration(file);
		
		configuration.addDefault(PREFIX + "Enabled", false);
		configuration.addDefault(PREFIX + "DefaultEnabled", true);
		configuration.addDefault(PREFIX + "GroupEnabled", false);
		
		configuration.addDefault(PREFIX + "Udates.AutoInterval", 60);
		configuration.addDefault(PREFIX + "Udates.onAfk", false); //TODO
		configuration.addDefault(PREFIX + "Udates.onJoin", false);
		configuration.addDefault(PREFIX + "Udates.onDeath", false);
		configuration.addDefault(PREFIX + "Udates.onTeleport", false);
		configuration.addDefault(PREFIX + "Udates.onWorldChange", false);
		
		if(!file.exists()) {
			List<String> headerFooter = new LinkedList<>();
			headerFooter.add("Default text");
			configuration.addDefault(PREFIX + "DefaultTablist.PlayerName", "§2[%name%]");
			configuration.addDefault(PREFIX + "DefaultTablist.Header", new LinkedList<>(headerFooter));
			configuration.addDefault(PREFIX + "DefaultTablist.Footer", new LinkedList<>(headerFooter));
			
			configuration.addDefault(PREFIX + "GroupTablist.1.PlayerName", "§4[%name%]");
			configuration.addDefault(PREFIX + "GroupTablist.1.Header", new LinkedList<>(headerFooter));
			configuration.addDefault(PREFIX + "GroupTablist.1.Footer", new LinkedList<>(headerFooter));
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
		
		onJoin = configuration.getBoolean(PREFIX + "Udates.onJoin");
		onDeath = configuration.getBoolean(PREFIX + "Udates.onDeath");
		onTeleport = configuration.getBoolean(PREFIX + "Udates.onTeleport");
		onWorldChange = configuration.getBoolean(PREFIX + "Udates.onWorldChange");
		
		if(enabled) {
			Bukkit.getPluginManager().registerEvents(tablistListener, Main.getPlugin());
			TablistTimer.start();
		}
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
		String playerName;
		
		if(number == -1) {
			header = configuration.getString(PREFIX + "DefaultTablist.Header");
			footer = configuration.getString(PREFIX + "DefaultTablist.Footer");
			playerName = configuration.getString(PREFIX + "DefaultTablist.PlayerName");
		} else {
			header = configuration.getString(PREFIX + "GroupTablist." + number + ".Header");
			footer = configuration.getString(PREFIX + "GroupTablist." + number + ".Header");
			playerName = configuration.getString(PREFIX + "GroupTablist." + number + ".PlayerName");
		}
		
		header = TablistFormatter.parseToString(player, header);
		footer = TablistFormatter.parseToString(player, footer);
		playerName = TablistFormatter.parseToString(player, playerName);
		
		TablistUtilities.sendHeaderFooter(player, header, footer);
	}
}
