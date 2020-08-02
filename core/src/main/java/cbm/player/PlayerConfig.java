package cbm.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import cbm.config.database.DatabaseConfig;
import cbm.config.database.DatabaseConfigManager;
import cbm.database.Databases;
import components.database.Database;
import components.database.DatabaseSyntax;

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

	public void set(PlayerConfigKey key, Object value) {
		set(key.toString(), value, false, false);
	}

	public Object get(PlayerConfigKey key) {
		return get(key.toString());
	}

	public boolean getBoolean(PlayerConfigKey key) {
		return getBoolean(key.toString());
	}

	public double getDouble(PlayerConfigKey key) {
		return getDouble(key.toString());
	}

	public int getInt(PlayerConfigKey key) {
		return getInt(key.toString());
	}

	public long getLong(PlayerConfigKey key) {
		return getLong(key.toString());
	}

	public String getString(PlayerConfigKey key) {
		return getString(key.toString());
	}

	public Location getLocation(PlayerConfigKey key) {
		return getLocation(key.toString());
	}

	public LocalDateTime getLocalDateTime(PlayerConfigKey key) {
		return getLocalDateTime(key.toString());
	}
	@Override
	protected ResultSet getDataInformation(String key) {
		PreparedStatement statement = null;
		try {
			statement = Databases.getPlayerDatabase().prepareStatement(DatabaseSyntax.selectFromWhere(key, "players", "uuid"));
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
	public boolean isAutomaticExtension() {
		return true;
	}

	@Override
	public DatabaseConfigManager<?, ?> getDatabaseConfig() {
		return PlayerManager.playerManager;
	}

	@Override
	public Database getDatabase() {
		return Databases.getPlayerDatabase();
	}
}
