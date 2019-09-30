package essentials.player;

import components.datenbank.DatabaseSyntax;
import essentials.database.Databases;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class PlayerConfig {

	public Map<String, PlayerConfigValue> buffer = Collections.synchronizedMap(new HashMap<>());
	public final UUID uuid;

	public PlayerConfig(UUID uuid) {
		this.uuid = uuid;

		OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
		if (player != null) set("name", player.getName());
	}

	public PlayerConfig(String uuidS) {
		uuid = UUID.fromString(uuidS);
	}

	public boolean containsLoadedKey(String key) {
		return buffer.containsKey(key);
	}

	public void set(PlayerConfigKey key, Object value) {
		set(key.toString(), value, false, false);
	}

	public void set(String key, Object value) {
		set(key, value, false, false);
	}

	public void setTmp(String key, Object value) {
		set(key, value, true, true);
	}

	public synchronized void set(String key, Object value, boolean saved, boolean tmp) {
		PlayerConfigValue playerConfigValue = buffer.get(key);

		if (playerConfigValue == null)
			buffer.put(key, new PlayerConfigValue(value, saved, tmp));
		else
			playerConfigValue.set(value);
	}

	public synchronized void removeBuffer(String key) {
		buffer.remove(key);
	}

	public Object get(PlayerConfigKey key) {
		return get(key.toString());
	}

	public Object get(String key) {
		PlayerConfigValue value = buffer.get(key);
		if (value != null)
			return value.getObject();

		try {
			ResultSet resultSet = getPlayerInformation(key);
			if (resultSet != null && resultSet.next())
				return resultSet.getObject(key);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean getBoolean(PlayerConfigKey key) {
		return getBoolean(key.toString());
	}

	public boolean getBoolean(String key) {
		PlayerConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof Boolean)
				return (boolean) value.getObject();
			return false;
		}

		try {
			ResultSet resultSet = getPlayerInformation(key);

			if (resultSet != null && resultSet.next())
				return resultSet.getBoolean(key);
		} catch (SQLException e) {}

		return false;
	}

	public double getDouble(PlayerConfigKey key) {
		return getDouble(key.toString());
	}

	public double getDouble(String key) {
		PlayerConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof Double)
				return (double) value.getObject();
			return 0;
		}

		try {
			ResultSet resultSet = getPlayerInformation(key);
			if (resultSet != null && resultSet.next())
				return resultSet.getDouble(key);
		} catch (SQLException e) {
		}

		return 0;
	}

	public int getInt(PlayerConfigKey key) {
		return getInt(key.toString());
	}

	public int getInt(String key) {
		PlayerConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof Integer)
				return (int) value.getObject();
			return 0;
		}

		try {
			ResultSet resultSet = getPlayerInformation(key);
			if (resultSet != null && resultSet.next())
				return resultSet.getInt(key);
		} catch (SQLException e) {}

		return 0;
	}

	public long getLong(PlayerConfigKey key) {
		return getLong(key.toString());
	}

	public long getLong(String key) {
		PlayerConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof Long)
				return (long) value.getObject();
			return 0;
		}

		try {
			ResultSet resultSet = getPlayerInformation(key);
			if (resultSet != null && resultSet.next())
				return resultSet.getLong(key);
		} catch (SQLException e) {
		}

		return 0;
	}

	public String getString(PlayerConfigKey key) {
		return getString(key.toString());
	}

	public String getString(String key) {
		PlayerConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof String)
				return (String) value.getObject();
			return value.getObject().toString();
		}

		try {
			ResultSet resultSet = getPlayerInformation(key);
			if (resultSet != null && resultSet.next())
				return resultSet.getString(key);
		} catch (SQLException e) {
		}

		return null;
	}

	public Location getLocation(PlayerConfigKey key) {
		return getLocation(key.toString());
	}

	public Location getLocation(String key) {
		PlayerConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof Location)
				return (Location) value.getObject();
			return null;
		}

		try {
			ResultSet resultSet = getPlayerInformation(key);
			if (resultSet != null && resultSet.next())
				return PlayerSQLHelper.StringToLocation(resultSet.getString(key));
		} catch (SQLException e) {
		}

		return null;
	}

	public LocalDateTime getLocalDateTime(PlayerConfigKey key) {
		return getLocalDateTime(key.toString());
	}

	public LocalDateTime getLocalDateTime(String key) {
		PlayerConfigValue value = buffer.get(key);
		if (value != null) {
			if (value.getObject() instanceof LocalDateTime)
				return (LocalDateTime) value.getObject();
			return null;
		}

		try {
			ResultSet resultSet = getPlayerInformation(key);
			if (resultSet != null && resultSet.next())
				return resultSet.getTimestamp(key).toLocalDateTime();
		} catch (SQLException e) {
		}
		return null;
	}

	private ResultSet getPlayerInformation(String key) {
		PreparedStatement statement = null;
		try {
			statement = Databases.getPlayerDatabase().prepareStatementWE(DatabaseSyntax.selectFromWhere(key, "players", "uuid"));
		} catch (SQLException e1) {
		} //No Such Coloum Exception
		if (statement == null) return null;

		try {
			statement.setString(1, uuid.toString());
			ResultSet resultSet = statement.executeQuery();
			if (PlayerManager.hasColoumn(key, resultSet))
				return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static boolean automaticExtension = true;

	public void save() {
		synchronized (buffer) {
			List<String> coloumns = PlayerManager.getColoumns();

			if (automaticExtension) {
				for (String key : buffer.keySet()) {
					PlayerConfigValue value = buffer.get(key);
					if (value.isSaved() || value.isTmp()) continue;

					if (!coloumns.contains(key)) {
						PreparedStatement statement = Databases.getPlayerDatabase().prepareStatement("ALTER TABLE players ADD COLUMN " + key + " " + PlayerSQLHelper.getSQLDataType(value.getObject()));
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
				builder.append("UPDATE players");

				List<String> conditions = new LinkedList<>();

				for (String key : buffer.keySet()) {
					if (key == null) continue;
					PlayerConfigValue value = buffer.get(key);
					if (value == null || value.isSaved() || value.isTmp() || (coloumns != null && !coloumns.contains(key)))
						continue;
					conditions.add(key);
				}

				builder.append('\n');
				String[] array = new String[conditions.size()];
				conditions.toArray(array);
				builder.append(DatabaseSyntax.setKeywordWithCondition("SET", array));
				builder.append('\n');
				builder.append(DatabaseSyntax.where("uuid"));

				try {
					preparedStatement = Databases.getPlayerDatabase().prepareStatementWE(builder.toString());
					if (preparedStatement == null) return;
				} catch (SQLException e) {
					System.out.println(builder.toString());
					e.printStackTrace();
				}
			}

			try {
				int index = 1;

				for (String key : buffer.keySet()) {
					if (key == null) continue;
					PlayerConfigValue value = buffer.get(key);
					if (value == null || value.isSaved() || value.isTmp() || (coloumns != null && !coloumns.contains(key)))
						continue;

					try {
						PlayerSQLHelper.set(preparedStatement, index++, value.getObject());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				preparedStatement.setString(index++, uuid.toString());

				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			buffer.clear();
		}
	}
}
