package essentials.modules.kits.player;

import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import components.datenbank.Datenbank;
import essentials.config.database.DatabaseConfigManager;
import essentials.database.Databases;

public class KitPlayerManager {
	private KitPlayerManager() {}

	static KitPlayerConfigManager manager; //TODO save on quit player
	
	static {
		manager = new KitPlayerConfigManager();
	}

	public static void unload() {
		manager.unloadAll();
	}
	
	public static void unload(UUID uuid) {
		manager.unload(uuid);
	}

	public static KitPlayerConfig getConfig(Player player) {
		return getConfig(player.getUniqueId());
	}
	
	public static KitPlayerConfig getConfig(UUID uuid) {
		return getConfig(uuid, true);
	}

	public static KitPlayerConfig getConfig(UUID id, boolean buffer) {
		return manager.getConfig(id, buffer);
	}

	public static void unloadAll() {
		manager.unloadAll();
	}
	
	public static KitPlayerConfigManager getManager() {
		return manager;
	}
	
	static class KitPlayerConfigManager extends DatabaseConfigManager<UUID, KitPlayerConfig> {
		@Override
		protected void insertOrIgnoreData(UUID uuid) {
			Datenbank database = Databases.getPlayerDatabase();
			database.execute("INSERT OR IGNORE INTO kit (uuid) VALUES ('" + uuid.toString() + "')");
		}

		@Override
		protected boolean shouldAddToBuffer(UUID uuid) {
			Player player = Bukkit.getPlayer(uuid);
			return (player != null) && player.isOnline();
		}

		@Override
		public KitPlayerConfig createConfig(UUID uuid) {
			return new KitPlayerConfig(uuid);
		}

		@Override
		protected ResultSet queryToReadColoumns() {
			Datenbank database = Databases.getPlayerDatabase();
			return database.getResult("SELECT * FROM kit LIMIT 1");
		}
	}
}
