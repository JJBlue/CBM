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
import essentials.database.Databases;

public class PlayerManager {
	private PlayerManager() {}
	
	protected static Map<UUID, PlayerConfig> players = Collections.synchronizedMap(new HashMap<>());
	
	public synchronized static void unload() {
		unloadAll();
	}
	
	public static PlayerConfig getPlayerConfig(Player player) {
		return getPlayerConfig(player.getUniqueId());
	}
	
	public static synchronized PlayerConfig getPlayerConfig(UUID uuid) {
		PlayerConfig playerConfig = players.get(uuid);
		
		if(playerConfig != null)
			return playerConfig;
		
		Datenbank database = Databases.getPlayerDatabase();
		database.execute("INSERT OR IGNORE INTO players (uuid) VALUES ('" + uuid.toString() + "')");
		
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
	
	static boolean hasColoumn(String coloum, ResultSet resultSet) {
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int coloumnCount = metaData.getColumnCount();
			
			for(int i = 1; i <= coloumnCount; i++) {
				if(metaData.getColumnName(i).equalsIgnoreCase(coloum))
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	static List<String> getColoumns() {
		List<String> coloumns = new LinkedList<>();
		
		Datenbank database = Databases.getPlayerDatabase();
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
