package cbm.database;

import java.sql.SQLException;

import cbm.config.MainConfig;
import cbm.player.PlayerManager;
import components.database.Database;
import components.database.DatabaseType;
import components.sql.SQLParser;

public class Databases {
	private static Database playerDatabase;
	private static Database worldDatabase;

	public static void load() {
		playerDatabase = new Database(null, null, MainConfig.getDataFolder() + "players.db");
		playerDatabase.connect(DatabaseType.SQLLite);

		for (String s : SQLParser.getResources("sql/create.sql", PlayerManager.class)) {
			try {
				playerDatabase.execute(s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		worldDatabase = new Database(null, null, MainConfig.getDataFolder() + "worlds.db");
		worldDatabase.connect(DatabaseType.SQLLite);
	}

	public static void unload() {
		if (playerDatabase != null)
			playerDatabase.close();

		if (worldDatabase != null)
			worldDatabase.close();
	}

	public static Database getWorldDatabase() {
		return worldDatabase;
	}

	public static Database getPlayerDatabase() {
		return playerDatabase;
	}
}
