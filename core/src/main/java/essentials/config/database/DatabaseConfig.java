package essentials.config.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;

import components.datenbank.DatabaseSyntax;
import components.datenbank.Datenbank;
import components.datenbank.async.AsyncDatabase;

public abstract class DatabaseConfig {

	public Map<String, DatabaseConfigValue> buffer = Collections.synchronizedMap(new HashMap<>());
	
	public abstract AbstractDatabaseConfig<?> getDatabaseConfig();
	public abstract Datenbank getDatabase();
	
	public boolean containsLoadedKey(String key) {
		return buffer.containsKey(key);
	}

	public void set(String key, Object value) {
		set(key, value, false, false);
	}
	
	public void set(String key, Object value, int type) {
		set(key, value, type, false, false);
	}

	public void setTmp(String key, Object value) {
		set(key, value, true, true);
	}
	
	public synchronized void set(String key, Object value, boolean saved, boolean tmp) {
		DatabaseConfigValue DatabaseConfigValue = buffer.get(key);

		if (DatabaseConfigValue == null)
			buffer.put(key, new DatabaseConfigValue(value, saved, tmp));
		else
			DatabaseConfigValue.set(value);
	}
	
	public synchronized void set(String key, Object value, int type, boolean saved, boolean tmp) {
		DatabaseConfigValue DatabaseConfigValue = buffer.get(key);

		if (DatabaseConfigValue == null)
			buffer.put(key, new DatabaseConfigValue(value, type, saved, tmp));
		else {
			DatabaseConfigValue.set(value);
			DatabaseConfigValue.setType(type);
		}
	}

	public synchronized void removeBuffer(String key) {
		buffer.remove(key);
	}

	public Object get(String key) {
		DatabaseConfigValue value = buffer.get(key);
		if (value != null)
			return value.getObject();

		try {
			ResultSet resultSet = getDataInformation(key);
			if (resultSet != null && resultSet.next())
				return resultSet.getObject(key);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean getBoolean(String key) {
		DatabaseConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof Boolean)
				return (boolean) value.getObject();
			return false;
		}

		try {
			ResultSet resultSet = getDataInformation(key);

			if (resultSet != null && resultSet.next()) {
				boolean v = resultSet.getBoolean(key);
				buffer.put(key, new DatabaseConfigValue(v, true));
				return v;
			}
		} catch (SQLException e) {}

		return false;
	}

	public double getDouble(String key) {
		DatabaseConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof Double)
				return (double) value.getObject();
			return 0;
		}

		try {
			ResultSet resultSet = getDataInformation(key);
			if (resultSet != null && resultSet.next()) {
				Double v = resultSet.getDouble(key);
				buffer.put(key, new DatabaseConfigValue(v, true));
				return v;
			}
		} catch (SQLException e) {}

		return 0;
	}

	public int getInt(String key) {
		DatabaseConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof Integer)
				return (int) value.getObject();
			return 0;
		}

		try {
			ResultSet resultSet = getDataInformation(key);
			if (resultSet != null && resultSet.next()) {
				int v = resultSet.getInt(key);
				buffer.put(key, new DatabaseConfigValue(v, true));
				return v;
			}
		} catch (SQLException e) {}

		return 0;
	}

	public long getLong(String key) {
		DatabaseConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof Long)
				return (long) value.getObject();
			return 0;
		}

		try {
			ResultSet resultSet = getDataInformation(key);
			if (resultSet != null && resultSet.next()) {
				Long v = resultSet.getLong(key);
				buffer.put(key, new DatabaseConfigValue(v, true));
				return v;
			}
		} catch (SQLException e) {}

		return 0;
	}

	public String getString(String key) {
		DatabaseConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof String)
				return (String) value.getObject();
			else if(value.getObject() == null)
				return null;
			return value.getObject().toString();
		}

		try {
			ResultSet resultSet = getDataInformation(key);
			if (resultSet != null && resultSet.next()) {
				String v = resultSet.getString(key);
				buffer.put(key, new DatabaseConfigValue(v, true));
				return v;
			}
		} catch (SQLException e) {}

		return null;
	}

	public Location getLocation(String key) {
		DatabaseConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof Location)
				return (Location) value.getObject();
			return null;
		}

		try {
			ResultSet resultSet = getDataInformation(key);
			if (resultSet != null && resultSet.next()) {
				Location v = SQLHelper.StringToLocation(resultSet.getString(key));
				buffer.put(key, new DatabaseConfigValue(v, true));
				return v;
			}
		} catch (SQLException e) {}

		return null;
	}

	public LocalDateTime getLocalDateTime(String key) {
		DatabaseConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof LocalDateTime)
				return (LocalDateTime) value.getObject();
			return null;
		}

		try {
			ResultSet resultSet = getDataInformation(key);
			if (resultSet != null && resultSet.next()) {
				Timestamp timestamp = resultSet.getTimestamp(key);
				if(timestamp == null) return null;
				LocalDateTime v = timestamp.toLocalDateTime();
				buffer.put(key, new DatabaseConfigValue(v, true));
				return v;
			}
		} catch (SQLException e) {}
		return null;
	}

	protected abstract ResultSet getDataInformation(String key);

	public void save() {
		synchronized (buffer) {
			if(buffer.isEmpty()) return;
			
			List<String> coloumns = null;

			if (isAutomaticExtension()) {
				coloumns = getDatabaseConfig().getColumns(); //set directly of update sql should not error with coloumn not found
				
				for (String key : buffer.keySet()) {
					DatabaseConfigValue value = buffer.get(key);
					if (value.isSaved() || value.isTmp()) continue;

					if (!coloumns.contains(key)) {
						PreparedStatement statement = getDatabase().prepareStatement("ALTER TABLE " + getTableName() + " ADD COLUMN " + key + " " + SQLHelper.getSQLDataType(value.getObject()));
						coloumns.add(key);

						try {
							statement.execute();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}

			PreparedStatement preparedStatement = null;
			{
				StringBuilder builder = new StringBuilder();
				builder.append("UPDATE " + getTableName());

				List<String> conditions = new LinkedList<>();

				for (String key : buffer.keySet()) {
					if (key == null) continue;
					DatabaseConfigValue value = buffer.get(key);
					if (value == null || value.isSaved() || value.isTmp() || (coloumns != null && !coloumns.contains(key)))
						continue;
					conditions.add(key);
				}
				
				if(conditions.isEmpty()) return;

				builder.append('\n');
				String[] array = new String[conditions.size()];
				conditions.toArray(array);
				builder.append(DatabaseSyntax.setKeywordWithCondition("SET", array));
				builder.append('\n');
				builder.append(saveWhereClause());

				try {
					preparedStatement = getDatabase().prepareStatementWE(builder.toString());
				} catch (SQLException e) {
					System.out.println(builder.toString());
					e.printStackTrace();
				}
				
				if (preparedStatement == null) return;
			}

			try {
				int index = 1;

				for (String key : buffer.keySet()) {
					if (key == null) continue;
					DatabaseConfigValue value = buffer.get(key);
					if (value == null || value.isSaved() || value.isTmp() || (coloumns != null && !coloumns.contains(key)))
						continue;

					try {
						SQLHelper.set(preparedStatement, index++, value.getObject());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				saveSetWhereClause(index, preparedStatement);

				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			buffer.clear();
		}
	}
	
	protected abstract String getTableName();
	protected abstract String saveWhereClause();
	protected abstract void saveSetWhereClause(int index, PreparedStatement preparedStatement) throws SQLException;
	
	public boolean isAutomaticExtension() {
		return false;
	}
	
	public void saveAsync() {
		AsyncDatabase.add(() -> save());
	}
}
