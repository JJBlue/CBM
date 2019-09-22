package essentials.modules.spawn;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import essentials.modules.teleport.TeleportManager;
import essentials.utilities.permissions.PermissionHelper;

public class SpawnManager {
	private SpawnManager() {}
	
	public static void teleportToSpawn(Entity entity) {
		TeleportManager.teleport(entity, getSpawnLocation(getSpawnName(entity)));
	}
	
	public static Location getSpawnLocation(int id) {
		return null; //TODO
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
