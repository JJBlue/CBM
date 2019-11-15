package essentials.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import components.datenbank.DatabaseSyntax;
import components.datenbank.Datenbank;
import essentials.config.database.DatabaseConfig;
import essentials.config.database.DatabaseConfigManager;
import essentials.database.Databases;

public class PlayerConfig extends DatabaseConfig {

	public final UUID uuid;

	public PlayerConfig(UUID uuid) {
		this.uuid = uuid;

		OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
		if (player != null) set("name", player.getName());
	}

	public PlayerConfig(String uuidS) {
		uuid = UUID.fromString(uuidS);
	}

	@Override
	protected ResultSet getDataInformation(String key) {
		PreparedStatement statement = null;
		try {
			statement = Databases.getPlayerDatabase().prepareStatementWE(DatabaseSyntax.selectFromWhere(key, "players", "uuid"));
		} catch (SQLException e1) {} //No Such Coloum Exception
		if (statement == null) return null;

		try {
			statement.setString(1, uuid.toString());
			ResultSet resultSet = statement.executeQuery();
			if (getDatabaseConfig().hasColumn(key, resultSet))
				return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected String getTableName() {
		return "players";
	}

	@Override
	protected String saveWhereClause() {
		return DatabaseSyntax.where("uuid");
	}

	@Override
	protected void saveSetWhereClause(int index, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(index++, uuid.toString());
	}

	@Override
	public DatabaseConfigManager<?, ?> getDatabaseConfig() {
		return PlayerManager.playerManager;
	}

	@Override
	public Datenbank getDatabase() {
		return Databases.getPlayerDatabase();
	}
}
