package cbm.config.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class DatabaseMapConfigManager<I extends Object, DI extends Object, D extends DatabaseConfig> implements AbstractDatabaseConfig<I> {
	
	protected Map<I, Map<DI, D>> configs = Collections.synchronizedMap(new HashMap<>());

	public synchronized void unload() {
		unloadAll();
	}

	public synchronized D getConfig(I uuid, DI did) {
		return getConfig(uuid, did, true);
	}

	public synchronized D getConfig(I id, DI did, boolean buffer) {
		Map<DI, D> config = configs.get(id);

		if (config != null && config.containsKey(did)) {
			return config.get(did);
		}

		try {
			insertOrIgnoreData(id, did);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		if (buffer) {
			if(shouldAddToBuffer(id, did))
				return load(id, did, true);
		}

		return load(id, did, false);
	}
	
	synchronized D load(I id, DI did, boolean buffer) {
		D playerConfig = createConfig(id, did);
		if (buffer) {
			Map<DI, D> config = configs.get(id);
			if(config == null) {
				config = Collections.synchronizedMap(new HashMap<>());
				configs.put(id, config);
			}
			
			config.put(did, playerConfig);
		}
		return playerConfig;
	}

	public synchronized void unload(I uuid) {
		Map<DI, D> config = configs.remove(uuid);
		
		for(D pcv : config.values()) {
			pcv.save();
		}
	}

	public synchronized void unloadAll() {
		for (Map<DI, D> map : configs.values()) {
			for(D pcv : map.values()) {
				pcv.save();
			}
		}

		configs.clear();
	}
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
	
	public abstract D createConfig(I id, DI did);
	protected abstract void insertOrIgnoreData(I id, DI did) throws SQLException;
	protected abstract boolean shouldAddToBuffer(I id, DI did);
	protected abstract ResultSet queryToReadColoumns() throws SQLException;
}
