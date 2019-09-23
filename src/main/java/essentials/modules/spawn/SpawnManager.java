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
	
	public static void teleportToSpawn(Entity entity) {
		TeleportManager.teleport(entity, getSpawnLocation(getSpawnName(entity)));
	}
	
	public synchronized static Location getSpawnLocation(int id) {
		if(spawns.containsKey(id))
			return spawns.get(id);
		
		Datenbank database = Databases.getWorldDatabase();
		
		//TODO create.sql
		PreparedStatement preparedStatement = database.prepareStatement(SQLParser.getResource("sql/create.sql", SpawnManager.class));
		try {
			preparedStatement.setString(1, id + "");
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
	
	public static int getSpawnName(Entity entity) {
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
}
