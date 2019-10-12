package components.json.finder;

import components.json.JSONArray;
import components.json.JSONObject;
import components.json.JSONValue;
import components.json.abstractJSON;

import java.time.LocalDateTime;
import java.util.List;

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
		if (key == null) return null;

		Object current = ajson;

		while (key.length() > 0) {
			if (current == null) return null;
			if (key.startsWith(".")) key = key.substring(1, key.length());

			if (current instanceof JSONObject) {
				//Contains JSONObject key
				Object o = ((JSONObject) current).get(key);
				if (o != null) {
					current = o;
					continue;
				}

				//Remove underKey from key
				String underKey = removeFromString(key);
				key = key.substring(underKey.length(), key.length());

				//Contains JSONObject underKey (key -> underKey.upperkey)
				o = ((JSONObject) current).get(underKey);
				if (o != null) {
					current = o;
					continue;
				} else return null;
			} else if (current instanceof JSONArray) {
				String underKey = removeFromString(key);
				key = key.substring(underKey.length(), key.length());

				if (underKey.startsWith("[") && underKey.endsWith("]")) {
					int index = Integer.parseInt(underKey.substring(1, underKey.length() - 1));
					current = ((JSONArray) current).get(index);
				}
			} else if (current instanceof JSONValue) {
				current = ((JSONValue) current).getValue();
			} else return current;
		}
		return current;
	}

	private static String removeFromString(String me) {
		StringBuilder key = new StringBuilder();

		if (me.length() > 0) {
			key.append(me.charAt(0));

			for (int i = 1; i < me.length(); i++) {
				char c = me.charAt(i);
				switch (c) {
					case '[':
					case '.':
						return key.toString();
					default:
						key.append(c);
				}
			}
		}

		return key.toString();
	}

	public static List<?> getList(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if (o == null) return null;
		if (o instanceof JSONArray) return ((JSONArray) o).getList();
		return null;
	}
	
	public static String getString(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if (o == null) return null;
		return o.toString();
	}

	public static Boolean getBoolean(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if (o == null) return false;
		if (o instanceof Boolean) return (boolean) o;
		return false;
	}

	public static byte getByte(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if (o == null) return 0;

		if (o instanceof Byte) return (byte) o;
		if (o instanceof Short) return (byte) (short) o;
		if (o instanceof Integer) return (byte) (int) o;
		if (o instanceof Long) return (byte) (long) o;

		try {
			return Byte.parseByte(o.toString());
		} catch (Exception e) {}

		return 0;
	}

	public static short getShort(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if (o == null) return 0;

		if (o instanceof Byte) return (short) (byte) o;
		if (o instanceof Short) return (short) o;
		if (o instanceof Integer) return (short) (int) o;
		if (o instanceof Long) return (short) (long) o;

		try {
			return Short.parseShort(o.toString());
		} catch (Exception e) {}

		return 0;
	}
	
	public static int getInt(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if (o == null) return 0;

		if (o instanceof Byte) return (byte) o;
		if (o instanceof Short) return (short) o;
		if (o instanceof Integer) return (int) o;
		if (o instanceof Long) return (int) (long) o;

		try {
			return Integer.parseInt(o.toString());
		} catch (Exception e) {}

		return 0;
	}

	public static long getLong(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if (o == null) return 0;

		if (o instanceof Byte) return (byte) o;
		if (o instanceof Short) return (short) o;
		if (o instanceof Integer) return (long) o;
		if (o instanceof Long) return (long) o;

		try {
			return Long.parseLong(o.toString());
		} catch (Exception e) {}

		return 0;
	}
	
	public static double getDouble(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if (o == null) return 0;

		if (o instanceof Float) return (double) ((float) o);
		if (o instanceof Double) return (double) o;

		try {
			return Double.parseDouble(o.toString());
		} catch (Exception e) {
		}

		return 0;
	}

	public static float getFloat(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if (o == null) return 0;

		if (o instanceof Float) return (float) o;

		try {
			return Float.parseFloat(o.toString());
		} catch (Exception e) {}

		return 0;
	}
	
	public static LocalDateTime getLocalDateTime(String key, abstractJSON ajson) {
		Object o = getObject(key, ajson);
		if (o == null) return null;
		return LocalDateTime.parse(o.toString());
	}
}
