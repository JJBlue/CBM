package essentials.database;

import components.datenbank.Datenbank;
import components.datenbank.Datenbanken;
import components.sql.SQLParser;
import essentials.config.MainConfig;
import essentials.player.PlayerManager;

public class Databases {
	private static Datenbank playerDatabase;
	private static Datenbank worldDatabase;

	public static void load() {
		playerDatabase = new Datenbank(null, null, MainConfig.getDataFolder() + "players.db");
		playerDatabase.connect(Datenbanken.SQLLite);

		for (String s : SQLParser.getResources("sql/create.sql", PlayerManager.class))
			playerDatabase.execute(s);

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
