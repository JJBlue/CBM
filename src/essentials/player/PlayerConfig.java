package essentials.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

public class PlayerConfig {
	
	public Map<String, PlayerConfigValue> buffer = Collections.synchronizedMap(new HashMap<>());
	public final UUID uuid;
	
	public PlayerConfig(UUID uuid) {
		this.uuid = uuid;
	}
	
	public synchronized void set(PlayerConfigKey key, Object value) {
		set(key.toString(), value, false, false);
	}
	
	public synchronized void set(String key, Object value) {
		set(key, value, false, false);
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
		
		//load ?
		
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
		
		//load
		
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
		
		//load
		
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
		
		//load
		
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
		
		//load
		
		return 0;
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
		
		//load
		
		return null;
	}
	
	public void save() {
		
	}
}
