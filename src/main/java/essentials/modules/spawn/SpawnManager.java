package essentials.modules.spawn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import components.datenbank.Datenbank;
import components.sql.SQLParser;
import essentials.database.Databases;
import essentials.modules.teleport.TeleportManager;
import essentials.player.PlayerSQLHelper;
import essentials.utilities.permissions.PermissionHelper;

public class SpawnManager {
	private SpawnManager() {}
	
	private static Map<Integer, Location> spawns = Collections.synchronizedMap(new HashMap<>());
	private static Map<String, Integer> spawnIDs = Collections.synchronizedMap(new HashMap<>());
	
	static {
		load();
	}
	
	public static void load() {
		Datenbank database = getDatabase();
		
		for(String s : SQLParser.getResources("sql/create.sql", SpawnManager.class))
			database.execute(s);
	}
	
	public static void teleportToSpawn(Entity entity) {
		TeleportManager.teleport(entity, getSpawnLocation(getSpawnID(entity)));
	}
	
	public static void teleportToSpawn(Entity entity, String name) {
		if(isNumber(name)) {
			teleportToSpawn(entity, Integer.parseInt(name));
			return;
		}
		TeleportManager.teleport(entity, getSpawnLocation(name));
	}
	
	public static void teleportToSpawn(Entity entity, int id) {
		TeleportManager.teleport(entity, getSpawnLocation(id));
	}
	
	public synchronized static Location getSpawnLocation(String name) {
		if(spawnIDs.containsKey(name))
			return spawns.get(spawnIDs.get(name));
		
		PreparedStatement preparedStatement = getPreparedStatement(SQLParser.getResource("sql/getLocationByName.sql", SpawnManager.class));
		try {
			preparedStatement.setString(1, name);
			ResultSet result = preparedStatement.executeQuery();
			if(result.next()) {
				Location location = PlayerSQLHelper.StringToLocation(result.getString("location"));
				spawns.put(result.getInt("ID"), location);
				spawnIDs.put(name, result.getInt("ID"));
				return location;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return getSpawnLocation(0);
	}
	
	public synchronized static Location getSpawnLocation(int id) {
		if(spawns.containsKey(id))
			return spawns.get(id);
		
		PreparedStatement preparedStatement = getPreparedStatement(SQLParser.getResource("sql/getLocationByID.sql", SpawnManager.class));
		try {
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			if(result.next()) {
				Location location = PlayerSQLHelper.StringToLocation(result.getString("location"));
				spawns.put(id, location);
				return location;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static int getSpawnID(Entity entity) {
		if(!(entity instanceof Player)) return 0;
		
		Player player = (Player) entity;
		int spawn_id = 0;
		
		for(PermissionAttachmentInfo pai : player.getEffectivePermissions()) {
			if(!pai.getPermission().contains(PermissionHelper.getPermission("spawn.id"))) continue;
			
			String id_name = pai.getPermission().substring("spawn.id".length(), pai.getPermission().length());
			
			try {
				int id = Integer.parseInt(id_name);
				if(spawn_id < id)
					spawn_id = id;
			} catch (NumberFormatException e) {}
		}
		
		return spawn_id;
	}

	public static boolean setSpawn(int id, String name, Location location) {
		if(id < 0) return false;
		if(isNumber(name)) return false;
		
		PreparedStatement preparedStatement = getPreparedStatement(SQLParser.getResource("sql/setSpawn.sql", SpawnManager.class));
		try {
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, PlayerSQLHelper.LocationToString(location));
			
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean deleteSpawn(int id) {
		PreparedStatement preparedStatement = getPreparedStatement(SQLParser.getResource("sql/deleteSpawn.sql", SpawnManager.class));
		try {
			preparedStatement.setInt(1, id);
			
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteSpawn(String name) {
		if(isNumber(name))
			return deleteSpawn(Integer.parseInt(name));
		
		PreparedStatement preparedStatement = getPreparedStatement(SQLParser.getResource("sql/deleteSpawnByName.sql", SpawnManager.class));
		
		try {
			preparedStatement.setString(1, name);
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static boolean isNumber(String name) {
		try {
			Integer.parseInt(name);
			return true;
		} catch (NumberFormatException e) {}
		return false;
	}
	
	public static PreparedStatement getPreparedStatement(String s) {
		return getDatabase().prepareStatement(s);
	}
	
	public static Datenbank getDatabase() {
		return Databases.getWorldDatabase();
	}
}
