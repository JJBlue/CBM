package essentials.tablist;

import essentials.config.MainConfig;
import essentials.main.Main;
import essentials.permissions.PermissionHelper;
import essentials.placeholder.PlaceholderFormatter;
import essentials.utilities.TablistUtilities;
import essentials.utilities.chat.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
	
	static Map<String, Integer> counterPageCurrent;
	
	static {
		tablistListener = new TablistListener();
		counterPageCurrent = Collections.synchronizedMap(new HashMap<>());
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
		
		List<String> headerFooter = new LinkedList<>();
		headerFooter.add("Default text");
		configuration.addDefault("DefaultTablist.PlayerName", "§2[%name%]");
		configuration.addDefault("DefaultTablist.Header.1", new LinkedList<>(headerFooter));
		configuration.addDefault("DefaultTablist.Header.Enabled", true);
		configuration.addDefault("DefaultTablist.Footer.1", new LinkedList<>(headerFooter));
		configuration.addDefault("DefaultTablist.Footer.Enabled", true);
		
		if(!file.exists()) {
			configuration.addDefault("GroupTablist.1.PlayerName", "§4[%name%]");
			configuration.addDefault("GroupTablist.1.Header.1", new LinkedList<>(headerFooter));
			configuration.addDefault("GroupTablist.1.Header.Enabled", true);
			configuration.addDefault("GroupTablist.1.Footer.1", new LinkedList<>(headerFooter));
			configuration.addDefault("GroupTablist.1.Footer.Enabled", true);
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
		String header = null;
		String footer = null;
		String playerName;
		
		if(number == -1) {
			
			if(!counterPageCurrent.containsKey("DefaultTablist"))
				counterPageCurrent.put("DefaultTablist", 1);
			
			int page = (int) counterPageCurrent.get("DefaultTablist");
			
			if(configuration.getBoolean("DefaultTablist.Header.Enabled"))
				header = ListToString(configuration.getList("DefaultTablist.Header." + page));
			if(configuration.getBoolean("DefaultTablist.Footer.Enabled"))
				footer = ListToString(configuration.getList("DefaultTablist.Footer." + page));
			playerName = configuration.getString("DefaultTablist.PlayerName");
		} else {
			
			if(!counterPageCurrent.containsKey("GroupTablist." + number))
				counterPageCurrent.put("GroupTablist." + number, 1);
			
			int page = (int) counterPageCurrent.get("GroupTablist." + number);
			
			if(configuration.getBoolean("GroupTablist." + number + ".Header.Enabled"))
				header = ListToString(configuration.getList("GroupTablist." + number + ".Header." + page));
			if(configuration.getBoolean("GroupTablist." + number + ".Footer.Enabled"))
				footer = ListToString(configuration.getList("GroupTablist." + number + ".Header." + page));
			playerName = configuration.getString("GroupTablist." + number + ".PlayerName");
		}
		
		if(header != null)
			header = ChatUtilities.convertToColor(PlaceholderFormatter.parseToString(player, header));
		if(footer != null)
			footer = ChatUtilities.convertToColor(PlaceholderFormatter.parseToString(player, footer));
		playerName = ChatUtilities.convertToColor(PlaceholderFormatter.parseToString(player, playerName));
		
		TablistUtilities.sendHeaderFooter(player, header, footer);
	}
	
	public static void nextTablist() {
		for(String prefixText : counterPageCurrent.keySet()) {
			if(!counterPageCurrent.containsKey(prefixText)) {
				counterPageCurrent.put(prefixText, 1);
				continue;
			}
			
			int cur = counterPageCurrent.get(prefixText);
			cur++;
			
			if(configuration.contains(prefixText + ".Header." + cur) || configuration.contains(prefixText + ".Footer." + cur)) {
				counterPageCurrent.put(prefixText, cur);
				continue;
			}
			
			counterPageCurrent.put(prefixText, 1);
		}
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