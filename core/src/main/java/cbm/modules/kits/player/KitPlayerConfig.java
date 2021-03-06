package cbm.modules.kits.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import cbm.config.database.AbstractDatabaseConfig;
import cbm.config.database.DatabaseConfig;
import cbm.database.Databases;
import components.database.Database;
import components.database.preparestatement.PrepareStatementBuilder;
import components.database.preparestatement.WhereStatement;

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
			statement = Databases.getPlayerDatabase().prepareStatement(
				new PrepareStatementBuilder()
					.select(key)
					.from(getTableName())
					.where(new WhereStatement()
						.object("uuid").equals()
					).build()
			);
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
		return new WhereStatement()
			.object("ID").equals()
			.object("uuid").equals()
			.build();
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
	public Database getDatabase() {
		return Databases.getPlayerDatabase();
	}
}
