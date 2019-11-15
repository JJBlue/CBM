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
import essentials.config.database.SQLHelper;
import essentials.database.Databases;
import essentials.modules.teleport.TeleportManager;
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
		teleportToSpawn(entity, true);
	}
	
	public static void teleportToSpawn(Entity entity, boolean delay) {
		if(delay)
			TeleportManager.teleport(entity, getSpawnLocation(getSpawnID(entity)));
		else {
			Location location = getSpawnLocation(getSpawnID(entity));
			if(location == null) return;
			entity.teleport(location);
		}
	}
	
	public static void teleportToSpawn(Entity entity, String name) {
		teleportToSpawn(entity, name, true);
	}
	
	public static void teleportToSpawn(Entity entity, String name, boolean delay) {
		if(delay) {
			if(isNumber(name)) {
				teleportToSpawn(entity, Integer.parseInt(name));
				return;
			}
			TeleportManager.teleport(entity, getSpawnLocation(name));
			
		} else {
			Location location = getSpawnLocation(name);
			if(location == null) return;
			entity.teleport(location);
		}
	}
	
	public static void teleportToSpawn(Entity entity, int id, boolean delay) {
		if(delay) {
			teleportToSpawn(entity, id);
		} else {
			Location location = getSpawnLocation(id);
			if(location == null) return;
			entity.teleport(location);
		}
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
				Location location = SQLHelper.StringToLocation(result.getString("location"));
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
				Location location = SQLHelper.StringToLocation(result.getString("location"));
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
		
		if(entity.isOp() || entity.hasPermission("*"))
			spawn_id = Integer.MAX_VALUE;
		else {
			for(PermissionAttachmentInfo pai : player.getEffectivePermissions()) {
				String permission = PermissionHelper.getPermission("spawn.id.");
				
				if(!pai.getPermission().contains(permission)) continue;
				
				String id_name = pai.getPermission().substring(permission.length(), pai.getPermission().length());
				
				try {
					int id = Integer.parseInt(id_name);
					if(spawn_id < id)
						spawn_id = id;
				} catch (NumberFormatException e) {}
			}
		}
		
		return spawn_id;
	}

	public static boolean setSpawn(int id, String name, Location location) {
		if(isNumber(name))
			return setSpawn(Integer.parseInt(name), "__" + name + "__", location);
		
		if(name.equalsIgnoreCase("firstjoin") || id == -1) {
			name = "firstjoin";
			id = -1;
		} else if(id < 0) return false;
		
		PreparedStatement preparedStatement = getPreparedStatement(SQLParser.getResource("sql/setSpawn.sql", SpawnManager.class));
		try {
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, SQLHelper.LocationToString(location));
			
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
