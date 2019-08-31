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
import essentials.utilities.chat.ChatUtilities;

public class Tablist {
	private Tablist() {}
	
	static File file;
	static FileConfiguration configuration;
	
	static boolean enabled;
	static boolean groupEnabled;
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
		
		configuration.addDefault("Enabled", false);
		configuration.addDefault("DefaultEnabled", true);
		configuration.addDefault("GroupEnabled", false);
		
		configuration.addDefault("Update.AutoInterval", 60);
		configuration.addDefault("Update.onAfk", false); //TODO
		configuration.addDefault("Update.onJoin", true);
		configuration.addDefault("Update.onDeath", false);
		configuration.addDefault("Update.onTeleport", false);
		configuration.addDefault("Update.onWorldChange", false);
		
		if(!file.exists()) {
			List<String> headerFooter = new LinkedList<>();
			headerFooter.add("Default text");
			configuration.addDefault("DefaultTablist.PlayerName", "§2[%name%]");
			configuration.addDefault("DefaultTablist.Header.1", new LinkedList<>(headerFooter));
			configuration.addDefault("DefaultTablist.Footer.1", new LinkedList<>(headerFooter));
			
			configuration.addDefault("GroupTablist.1.PlayerName", "§4[%name%]");
			configuration.addDefault("GroupTablist.1.Header.1", new LinkedList<>(headerFooter));
			configuration.addDefault("GroupTablist.1.Footer.1", new LinkedList<>(headerFooter));
		}
		
		configuration.options().copyDefaults(true);
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Loading
		enabled = configuration.getBoolean("Enabled");
		defaultEnabled = configuration.getBoolean("DefaultEnabled");
		groupEnabled = configuration.getBoolean("GroupEnabled");
		TablistTimer.setSleep(configuration.getInt("Update.AutoInterval") * 1000);
		
		onJoin = configuration.getBoolean("Update.onJoin");
		onDeath = configuration.getBoolean("Update.onDeath");
		onTeleport = configuration.getBoolean("Update.onTeleport");
		onWorldChange = configuration.getBoolean("Update.onWorldChange");
		
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
			header = ListToString(configuration.getList("DefaultTablist.Header.1"));
			footer = ListToString(configuration.getList("DefaultTablist.Footer.1"));
			playerName = configuration.getString("DefaultTablist.PlayerName");
		} else {
			header = ListToString(configuration.getList("GroupTablist." + number + ".Header.1"));
			footer = ListToString(configuration.getList("GroupTablist." + number + ".Header.1"));
			playerName = configuration.getString("GroupTablist." + number + ".PlayerName");
		}
		
		header = ChatUtilities.convertToColor(TablistFormatter.parseToString(player, header));
		footer = ChatUtilities.convertToColor(TablistFormatter.parseToString(player, footer));
		playerName = ChatUtilities.convertToColor(	TablistFormatter.parseToString(player, playerName));
		
		TablistUtilities.sendHeaderFooter(player, header, footer);
	}
	
	public static String ListToString(List<?> list) {
		if(list == null) return "";
		
		StringBuilder builder = new StringBuilder();
		boolean start = true;
		
		for(Object obj : list) {
			if(start) start = false;
			else builder.append('\n');
			
			if(obj == null) continue;
			else if(obj instanceof String)
				builder.append((String) obj);
			else
				builder.append(obj.toString());
		}
		return builder.toString();
	}
}
