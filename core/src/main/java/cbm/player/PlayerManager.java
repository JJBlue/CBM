package cbm.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import cbm.config.database.DatabaseConfigManager;
import cbm.database.Databases;
import components.database.Datenbank;

public class PlayerManager {
	private PlayerManager() {}

	static PlayerConfigManager playerManager;
	
	static {
		playerManager = new PlayerConfigManager();
	}

	public static void unload() {
		playerManager.unloadAll();
	}
	
	public static void unload(UUID uuid) {
		playerManager.unload(uuid);
	}

	public static PlayerConfig getConfig(Player player) {
		return getConfig(player.getUniqueId());
	}
	
	public static PlayerConfig getConfig(UUID uuid) {
		return getConfig(uuid, true);
	}

	public static PlayerConfig getConfig(UUID id, boolean buffer) {
		return playerManager.getConfig(id, buffer);
	}

	public static void unloadAll() {
		playerManager.unloadAll();
	}
	
	public static PlayerConfigManager getManager() {
		return playerManager;
	}
	
	static class PlayerConfigManager extends DatabaseConfigManager<UUID, PlayerConfig> {
		@Override
		public synchronized void unloadAll() {
			for (Player player : Bukkit.getOnlinePlayers())
				PlayerListener.quit(player);
			super.unloadAll();
		}

		@Override
		protected void insertOrIgnoreData(UUID uuid) throws SQLException {
			Datenbank database = Databases.getPlayerDatabase();
			database.execute("INSERT OR IGNORE INTO players (uuid) VALUES ('" + uuid.toString() + "')");
		}

		@Override
		protected boolean shouldAddToBuffer(UUID uuid) {
			Player player = Bukkit.getPlayer(uuid);
			return (player != null) && player.isOnline();
		}

		@Override
		public PlayerConfig createConfig(UUID uuid) {
			return new PlayerConfig(uuid);
		}

		@Override
		protected ResultSet queryToReadColoumns() throws SQLException {
			Datenbank database = Databases.getPlayerDatabase();
			return database.executeQuery("SELECT * FROM players LIMIT 1");
		}
	}

}
