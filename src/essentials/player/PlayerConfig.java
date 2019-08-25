package essentials.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

import components.datenbank.DatabaseSyntax;
import components.sql.SQLParser;

public class PlayerConfig {
	
	public Map<String, PlayerConfigValue> buffer = Collections.synchronizedMap(new HashMap<>());
	public final UUID uuid;
	
	public PlayerConfig(UUID uuid) {
		this.uuid = uuid;
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
		
		if(playerConfigValue == null)
			buffer.put(key, new PlayerConfigValue(value, saved, tmp));
		else
			playerConfigValue.set(value);
	}
	
	public synchronized void removeBuffer(String key) {
		buffer.remove(key);
	}
	
	public Object get(String key) {
		PlayerConfigValue value = buffer.get(key);
		if(value != null)
			return value.getObject();
		
		try {
			ResultSet resultSet = getPlayerInformation(key);
			if(resultSet != null && resultSet.next())
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
		if(value != null) {
			if(value.getObject() instanceof Boolean)
				return (boolean) value.getObject();
			return false;
		}
		
		try {
			ResultSet resultSet = getPlayerInformation(key);
			if(resultSet != null && resultSet.next())
				return resultSet.getBoolean(key);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public double getDouble(PlayerConfigKey key) {
		return getDouble(key.toString());
	}
	
	public double getDouble(String key) {
		PlayerConfigValue value = buffer.get(key);
		if(value != null) {
			if(value.getObject() instanceof Double)
				return (double) value.getObject();
			return 0;
		}
		
		try {
			ResultSet resultSet = getPlayerInformation(key);
			if(resultSet != null && resultSet.next())
				return resultSet.getDouble(key);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int getInt(PlayerConfigKey key) {
		return getInt(key.toString());
	}
	
	public int getInt(String key) {
		PlayerConfigValue value = buffer.get(key);
		if(value != null) {
			if(value.getObject() instanceof Integer)
				return (int) value.getObject();
			return 0;
		}
		
		
		try {
			ResultSet resultSet = getPlayerInformation(key);
			if(resultSet != null && resultSet.next())
				return resultSet.getInt(key);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public long getLong(PlayerConfigKey key) {
		return getLong(key.toString());
	}
	
	public long getLong(String key) {
		PlayerConfigValue value = buffer.get(key);
		if(value != null) {
			if(value.getObject() instanceof Long)
				return (long) value.getObject();
			return 0;
		}
		
		try {
			ResultSet resultSet = getPlayerInformation(key);
			if(resultSet != null && resultSet.next())
				return resultSet.getLong(key);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public String getString(PlayerConfigKey key) {
		return getString(key.toString());
	}
	
	public String getString(String key) {
		PlayerConfigValue value = buffer.get(key);
		if(value != null) {
			if(value.getObject() instanceof String)
				return (String) value.getObject();
			return value.getObject().toString();
		}
		
		try {
			ResultSet resultSet = getPlayerInformation(key);
			if(resultSet != null && resultSet.next())
				return resultSet.getString(key);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Location getLocation(PlayerConfigKey key) {
		return getLocation(key.toString());
	}
	
	public Location getLocation(String key) {
		PlayerConfigValue value = buffer.get(key);
		if(value != null) {
			if(value.getObject() instanceof Location)
				return (Location) value.getObject();
			return null;
		}
		
		//TODO load
		
		return null;
	}
	
	private ResultSet getPlayerInformation(String key) {
		PreparedStatement statement = PlayerManager.database.prepareStatement(SQLParser.getResource("sql/getPlayerInformation.sql", PlayerConfig.class));
		
		try {
			statement.setString(1, key);
			statement.setString(2, uuid.toString());
			return statement.executeQuery();
		} catch (SQLException e) {}
		return null;
	}
	
	private static boolean automaticExtension = true;
	public void save() {
		synchronized (buffer) {
			List<String> coloumns = null;
			if(automaticExtension) coloumns = PlayerManager.getColoumns();
			
			PreparedStatement preparedStatement;
			{
				StringBuilder builder = new StringBuilder();
				builder.append("UPDATE players");
				
				String[] conditions = new String[buffer.keySet().size()];
				int count = 0;
				for(String key : buffer.keySet())
					conditions[count++] = key;
				
				builder.append('\n');
				builder.append(DatabaseSyntax.setKeywordWithCondition("SET", conditions));
				builder.append('\n');
				builder.append(DatabaseSyntax.where("uuid"));
				
				preparedStatement = PlayerManager.database.prepareStatement(builder.toString());
			}
			
			try {
				int index = 1;
				
				for(String key : buffer.keySet()) {
					PlayerConfigValue value = buffer.get(key);
					if(value.isSaved() || value.isTmp()) continue;
					
					if(automaticExtension && !coloumns.contains(key)) {
						PreparedStatement statement = PlayerManager.database.prepareStatement("ALTER TABLE players ADD COLUMN " + key + " " + getSQLDataType(key));
						try {
							statement.execute();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
					try {
						set(preparedStatement, index++, value.getObject());
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
	
	private static void set(PreparedStatement preparedStatement, int index, Object obj) throws SQLException {
		if(obj instanceof Boolean)
			preparedStatement.setBoolean(index, (Boolean) obj);
		else if(obj instanceof Byte)
			preparedStatement.setByte(index, (Byte) obj);
		else if(obj instanceof Integer)
			preparedStatement.setInt(index, (Integer) obj);
		else if(obj instanceof Long)
			preparedStatement.setLong(index, (Long) obj);
		else if(obj instanceof Float)
			preparedStatement.setFloat(index, (Float) obj);
		else if(obj instanceof Double)
			preparedStatement.setDouble(index, (Double) obj);
		else if(obj instanceof LocalDate)
			preparedStatement.setDate(index, java.sql.Date.valueOf((LocalDate) obj));
		else if(obj instanceof java.sql.Date)
			preparedStatement.setDate(index, (java.sql.Date) obj);
		else if(obj instanceof LocalTime)
			preparedStatement.setTime(index, java.sql.Time.valueOf((LocalTime) obj));
		else if(obj instanceof java.sql.Time)
			preparedStatement.setTime(index, (java.sql.Time) obj);
		else if(obj instanceof LocalDateTime)
			preparedStatement.setTimestamp(index, java.sql.Timestamp.valueOf((LocalDateTime) obj));
		else if(obj instanceof java.sql.Timestamp)
			preparedStatement.setTimestamp(index, (java.sql.Timestamp) obj);
		else
			preparedStatement.setString(index, obj.toString());
	}
	
	private static String getSQLDataType(Object obj) {
		if(obj instanceof Boolean)
			return "BOOL";
		else if(obj instanceof Byte)
			return "TINYINT";
		else if(obj instanceof Character)
			return "CHAR";
		else if(obj instanceof Short)
			return "SMALLINT";
		else if(obj instanceof Integer)
			return "INT";
		else if(obj instanceof Long)
			return "BIGINT";
		else if(obj instanceof Float)
			return "FLOAT";
		else if(obj instanceof Double)
			return "DOUBLE";
		else if(obj instanceof LocalDate || obj instanceof java.sql.Date)
			return "DATE";
		else if(obj instanceof LocalTime || obj instanceof java.sql.Time)
			return "TIME";
		else if(obj instanceof LocalDateTime || obj instanceof java.sql.Timestamp)
			return "TIMESTAMP";
		else {
			String s = (String) obj;
			int length = Math.max(s.length(), 100);
			return "VARCHAR(" + length + ")";
		}
	}
}
