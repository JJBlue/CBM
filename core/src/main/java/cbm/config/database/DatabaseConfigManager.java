package cbm.config.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class DatabaseConfigManager<I extends Object, D extends DatabaseConfig> implements AbstractDatabaseConfig<I> {
	
	protected Map<I, D> configs = Collections.synchronizedMap(new HashMap<>());

	@Override
	public synchronized void unload() {
		unloadAll();
	}

	public synchronized D getConfig(I uuid) {
		return getConfig(uuid, true);
	}

	public synchronized D getConfig(I id, boolean buffer) {
		D config = configs.get(id);

		if (config != null)
			return config;

		try {
			insertOrIgnoreData(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		if (buffer) {
			if(shouldAddToBuffer(id))
				return load(id, true);
		}

		return load(id, false);
	}
	
	synchronized D load(I id, boolean buffer) {
		D playerConfig = createConfig(id);
		if (buffer)
			configs.put(id, playerConfig);
		return playerConfig;
	}

	@Override
	public synchronized void unload(I uuid) {
		D config = configs.remove(uuid);
		config.save();
	}

	@Override
	public synchronized void unloadAll() {
		for (D pcv : configs.values())
			pcv.save();

		configs.clear();
	}
	
	@Override
	public boolean hasColumn(String column, ResultSet resultSet) {
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				if (metaData.getColumnName(i).equalsIgnoreCase(column))
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<String> getColumns() {
		List<String> columns = new LinkedList<>();

		try {
			ResultSet resultSet = queryToReadColoumns();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			for (int i = 1; i <= columnCount; i++)
				columns.add(metaData.getColumnName(i));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columns;
	}
	
	public abstract D createConfig(I id);
	protected abstract void insertOrIgnoreData(I id) throws SQLException;
	protected abstract boolean shouldAddToBuffer(I id);
	protected abstract ResultSet queryToReadColoumns() throws SQLException;
}
