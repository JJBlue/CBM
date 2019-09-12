package essentials.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PlayerSQLHelper {
	
	public static String LocationToString(Location location) {
		String builder = location.getWorld().getName() +
				':' +
				round(location.getX()) +
				':' +
				round(location.getY()) +
				':' +
				round(location.getZ()) +
				':' +
				round(location.getYaw()) +
				':' +
				round(location.getPitch());
		return builder;
	}
	
	public static Location StringToLocation(String loc) {
		String[] locSplit = loc.split(":");
		if(locSplit.length < 4) return null;
		
		try {
			World world = Bukkit.getWorld(locSplit[0]);
			double x = Double.parseDouble(locSplit[1]);
			double y = Double.parseDouble(locSplit[2]);
			double z = Double.parseDouble(locSplit[3]);
			
			if(locSplit.length < 6)
				return new Location(world, x, y, z);
			
			float yaw = Float.parseFloat(locSplit[4]);
			float pitch = Float.parseFloat(locSplit[5]);
			
			return new Location(world, x, y, z, yaw, pitch);
		} catch (NumberFormatException e) {}
		return null;
	}
	
	public static double round(double value) {
		long lv = (long) (value * 100);
		return (double) lv / 100d;
	}
	
	public static float round(float value) {
		long lv = (long) (value * 100);
		return (float) lv / 100f;
	}
	
	public static void set(PreparedStatement preparedStatement, int index, Object obj) throws SQLException {
		if(preparedStatement == null) return;
		
		if(obj == null)
			preparedStatement.setString(index, null);
		else if(obj instanceof Boolean)
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
			preparedStatement.setDate(index, java.sql.Date.valueOf(obj != null ? (LocalDate) obj : LocalDate.now()));
		else if(obj instanceof java.sql.Date)
			preparedStatement.setDate(index, (java.sql.Date) obj);
		else if(obj instanceof LocalTime)
			preparedStatement.setTime(index, java.sql.Time.valueOf(obj != null ? (LocalTime) obj : LocalTime.now()));
		else if(obj instanceof java.sql.Time)
			preparedStatement.setTime(index, (java.sql.Time) obj);
		else if(obj instanceof LocalDateTime)
			preparedStatement.setTimestamp(index, java.sql.Timestamp.valueOf(obj != null ? (LocalDateTime) obj : LocalDateTime.now()));
		else if(obj instanceof java.sql.Timestamp)
			preparedStatement.setTimestamp(index, (java.sql.Timestamp) obj);
		else if(obj instanceof Location)
			preparedStatement.setString(index, LocationToString((Location) obj));
		else
			preparedStatement.setString(index, obj.toString());
	}
	
	public static String getSQLDataType(Object obj) {
		if(obj instanceof Boolean)
			return "BOOLEAN";
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
			int length = 0;
			
			if(obj == null)
				length = 100;
			else if(obj instanceof String) {
				String s = (String) obj;
				length = Math.max(s.length(), 100);
			} else
				length = Math.max(obj.toString().length(), 100);
			
			return "VARCHAR(" + length + ")";  // TODO VARCHAR(size) -> TEXT
		}
	}
}
