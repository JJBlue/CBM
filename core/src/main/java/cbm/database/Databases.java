package cbm.database;

import java.sql.SQLException;

import cbm.config.MainConfig;
import cbm.player.PlayerManager;
import components.database.Datenbank;
import components.database.Datenbanken;
import components.sql.SQLParser;

public class Databases {
	private static Datenbank playerDatabase;
	private static Datenbank worldDatabase;

	public static void load() {
		playerDatabase = new Datenbank(null, null, MainConfig.getDataFolder() + "players.db");
		playerDatabase.connect(Datenbanken.SQLLite);

		for (String s : SQLParser.getResources("sql/create.sql", PlayerManager.class)) {
			try {
				playerDatabase.execute(s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		worldDatabase = new Datenbank(null, null, MainConfig.getDataFolder() + "worlds.db");
		worldDatabase.connect(Datenbanken.SQLLite);
	}

	public static void unload() {
		if (playerDatabase != null)
			playerDatabase.close();

		if (worldDatabase != null)
			worldDatabase.close();
	}

	public static Datenbank getWorldDatabase() {
		return worldDatabase;
	}

	public static Datenbank getPlayerDatabase() {
		return playerDatabase;
	}
}
