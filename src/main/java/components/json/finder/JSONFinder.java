package components.json.finder;

import components.json.JSONArray;
import components.json.JSONObject;
import components.json.JSONValue;
import components.json.abstractJSON;

import java.time.LocalDateTime;

public class JSONFinder {
	
	private abstractJSON ajson;
	
	public JSONFinder(abstractJSON ajson) {
		this.ajson = ajson;
	}
	
	public Object getObject(String key) {
		return getObject(key, ajson);
	}

	public String getString(String key) {
		return getString(key, ajson);
	}
	
	public int getInt(String key) {
		return getInt(key, ajson);
	}
	
	//----------------------------------------STATIC---------------------------------------
	public static Object getObject(String key, abstractJSON ajson) {
		if(key == null)return null;
		
		Object current = ajson;
		
		while(key.length() > 0) {
			if(current == null)return null;
			if(key.startsWith("."))key = key.substring(1, key.length());
			
			if(current instanceof JSONObject) {
				//Contains JSONObject key
				Object o = ((JSONObject) current).get(key);
				if(o != null) {
					current = o;
					continue;
				}
				
				//Remove underKey from key
				String underKey = removeFromString(key);
				key = key.substring(underKey.length(), key.length());
				
				//Contains JSONObject underKey (key -> underKey.upperkey)
				o = ((JSONObject) current).get(underKey);
				if(o != null) {
					current = o;
					continue;
				}
				else return null;
			}else if(current instanceof JSONArray) {
				String underKey = removeFromString(key);
				key = key.substring(underKey.length(), key.length());
				
				if(underKey.startsWith("[") && underKey.endsWith("]")) {
					int index = Integer.parseInt(underKey.substring(1, underKey.length() - 1));
					current = ((JSONArray) current).get(index);
				}
			}else if(current instanceof JSONValue) {
				current = ((JSONValue) current).getValue();
			}
			else return current;
		}
		return current;
	}
	
	private static String removeFromString(String me) {
		StringBuilder key = new StringBuilder();
		
		if(me.length() > 0) {
			key.append(me.charAt(0));
			
			for(int i = 1; i < me.length(); i++) {
				char c = me.charAt(i);
				switch(c) {
					case '[':
						return key.toString();
					case '.':
						return key.toString();
					default:
						key.append(c);
				}
			}
		}
		
		return key.toString();
	}

	public static String getString(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if(o == null) return null;
		return o.toString();
	}
	
	public static Boolean getBoolean(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if(o == null) return null;
		
		if(o instanceof Boolean) return (boolean) o;
		return false;
	}
	
	public static int getInt(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if(o == null) return 0;
		
		if(o instanceof Integer)return (int) o;
		if(o instanceof Long)return (int) ((long) o);
		
		try {
			return Integer.parseInt(o.toString());
		}catch(Exception e) {}
		
		return 0;
	}
	
	public static double getDouble(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if(o == null) return 0;
		
		if(o instanceof Float)return (double) ((float) o);
		if(o instanceof Double)return (double) o;
		
		try {
			return Double.parseDouble(o.toString());
		}catch(Exception e) {}
		
		return 0;
	}

	public static LocalDateTime getLocalDateTime(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if(o == null) return null;
		return LocalDateTime.parse(o.toString());
	}
}
