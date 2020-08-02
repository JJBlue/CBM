package cbm.modules.kits.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import cbm.config.database.DatabaseMapConfigManager;
import cbm.database.Databases;
import components.database.Database;
import components.sql.SQLParser;

public class KitPlayerManager {
	private KitPlayerManager() {}

	static KitPlayerConfigManager manager;
	
	static {
		manager = new KitPlayerConfigManager();
		
		for(String update : SQLParser.getResources("/sql/create.sql", KitPlayerManager.class)) {
			try {
				Databases.getPlayerDatabase().execute(update);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void unload() {
		manager.unloadAll();
	}
	
	public static void unload(UUID uuid) {
		manager.unload(uuid);
	}

	public static KitPlayerConfig getConfig(Player player, String did) {
		return getConfig(player.getUniqueId(), did);
	}
	
	public static KitPlayerConfig getConfig(UUID uuid, String did) {
		return getConfig(uuid, did, true);
	}

	public static KitPlayerConfig getConfig(UUID id, String did, boolean buffer) {
		return manager.getConfig(id, did, buffer);
	}

	public static void unloadAll() {
		manager.unloadAll();
	}
	
	public static KitPlayerConfigManager getManager() {
		return manager;
	}
	
	static class KitPlayerConfigManager extends DatabaseMapConfigManager<UUID, String, KitPlayerConfig> {
		@Override
		protected void insertOrIgnoreData(UUID uuid, String did) throws SQLException {
			Database database = Databases.getPlayerDatabase();
			database.execute("INSERT OR IGNORE INTO kitsPlayer (kitID, uuid) VALUES ('" + did + "', '" + uuid.toString() + "')");
		}

		@Override
		protected boolean shouldAddToBuffer(UUID uuid, String did) {
			Player player = Bukkit.getPlayer(uuid);
			return (player != null) && player.isOnline();
		}

		@Override
		public KitPlayerConfig createConfig(UUID uuid, String did) {
			return new KitPlayerConfig(did, uuid);
		}

		@Override
		protected ResultSet queryToReadColoumns() throws SQLException {
			Database database = Databases.getPlayerDatabase();
			return database.executeQuery("SELECT * FROM kitsPlayer LIMIT 1");
		}
	}
}
