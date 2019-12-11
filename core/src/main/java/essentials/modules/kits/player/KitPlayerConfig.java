package essentials.modules.kits.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import components.datenbank.DatabaseSyntax;
import components.datenbank.Datenbank;
import essentials.config.database.AbstractDatabaseConfig;
import essentials.config.database.DatabaseConfig;
import essentials.database.Databases;

public class KitPlayerConfig extends DatabaseConfig {
	
	public final UUID uuid;
	public final String kit;

	public KitPlayerConfig(String kit, UUID uuid) {
		this.kit = kit;
		this.uuid = uuid;
	}

	@Override
	protected ResultSet getDataInformation(String key) {
		PreparedStatement statement = null;
		try {
			statement = Databases.getPlayerDatabase().prepareStatementWE(DatabaseSyntax.selectFromWhere(key, getTableName(), "uuid"));
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
		return "kitsPlayer";
	}

	@Override
	protected String saveWhereClause() {
		return DatabaseSyntax.where("ID", "uuid");
	}

	@Override
	protected void saveSetWhereClause(int index, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(index++, kit);
		preparedStatement.setString(index++, uuid.toString());
	}

	@Override
	public AbstractDatabaseConfig<?> getDatabaseConfig() {
		return KitPlayerManager.manager;
	}

	@Override
	public Datenbank getDatabase() {
		return Databases.getPlayerDatabase();
	}
}
