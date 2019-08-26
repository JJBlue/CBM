package essentials.player;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import components.datenbank.Datenbank;
import components.datenbank.Datenbanken;
import components.sql.SQLParser;
import essentials.config.MainConfig;

public class PlayerManager {
	private PlayerManager() {}
	
	protected static Datenbank database;
	protected static Map<UUID, PlayerConfig> players;
	
	public synchronized static void load() {
		players = Collections.synchronizedMap(new HashMap<>());
		
		database = new Datenbank(null, null, MainConfig.getDataFolder() + "/players.db");
		database.connect(Datenbanken.SQLLite);
		
		for(String s : SQLParser.getResources("sql/create.sql", PlayerManager.class))
			database.execute(s);
	}
	
	public synchronized static void unload() {
		unloadAll();
		database.close();
	}
	
	public static PlayerConfig getPlayerConfig(Player player) {
		return getPlayerConfig(player.getUniqueId());
	}
	
	public static synchronized PlayerConfig getPlayerConfig(UUID uuid) {
		PlayerConfig playerConfig = players.get(uuid);
		
		if(playerConfig != null)
			return playerConfig;
		
		String name = null;
		{
			Player player = Bukkit.getPlayer(uuid);
			if(player != null) name = player.getName();
		}
		
		database.execute("REPLACE INTO players (uuid, name) VALUES ('" + uuid.toString() + "','" + name + "')");
		
		Player player = Bukkit.getPlayer(uuid);
		if(player != null && player.isOnline())
			return load(uuid, true);
		return load(uuid, false);
	}
	
	synchronized static PlayerConfig load(UUID uuid, boolean buffer) {
		PlayerConfig playerConfig = new PlayerConfig(uuid);
		if(buffer)
			players.put(uuid, playerConfig);
		return playerConfig;
	}
	
	synchronized static void unload(UUID uuid) {
		PlayerConfig playerConfig = players.remove(uuid);
		playerConfig.save();
	}
	
	public synchronized static void unloadAll() {
		for(PlayerConfig pcv : players.values())
			pcv.save();
		
		players.clear();
	}
	
	static List<String> getColoumns() {
		List<String> coloumns = new LinkedList<>();
		
		ResultSet resultSet = database.getResult("SELECT * FROM players LIMIT 1");
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int coloumnCount = metaData.getColumnCount();
			
			for(int i = 1; i <= coloumnCount; i++)
				coloumns.add(metaData.getColumnName(i));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coloumns;
	}
}
