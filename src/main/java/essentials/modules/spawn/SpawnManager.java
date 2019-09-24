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
	
	private static Map<String, Location> spawns = Collections.synchronizedMap(new HashMap<>());
	
	static {
		load();
	}
	
	public static void load() {
		Datenbank database = Databases.getWorldDatabase();
		
		for(String s : SQLParser.getResources("sql/create.sql", SpawnManager.class))
			database.execute(s);
	}
	
	public static void teleportToSpawn(Entity entity) {
		TeleportManager.teleport(entity, getSpawnLocation(getSpawnName(entity)));
	}
	
	public static void teleportToSpawn(Entity entity, String id) {
		TeleportManager.teleport(entity, getSpawnLocation(id));
	}
	
	public synchronized static Location getSpawnLocation(String id) {
		if(spawns.containsKey(id))
			return spawns.get(id);
		
		Datenbank database = Databases.getWorldDatabase();
		
		PreparedStatement preparedStatement = database.prepareStatement(SQLParser.getResource("sql/getLocation.sql", SpawnManager.class));
		try {
			preparedStatement.setString(1, id);
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
	
	public static String getSpawnName(Entity entity) {
		if(!(entity instanceof Player)) return "default";
		
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
		
		return spawn_id + "";
	}

	public static void setSpawn(int id, Location location) {
		
	}
	
	public static void deleteSpawn(int id) {
		
	}
}
