package components.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JSONObject extends abstractJSON {
	private Map<String, abstractJSON> map;

	public JSONObject(Map<?, ?> map) {
		this.map = new HashMap<>();
	}

	public JSONObject() {
		map = new HashMap<>();
	}

	public void add(String key, Object value) {
		map.put(key, JSONValue.getabstractJSON(value));
	}
	
	public boolean contains(String key) {
		return map.containsKey(key);
	}

	public abstractJSON get(String key) {
		return map.get(key);
	}

	public void clear() {
		map.clear();
	}

	public Map<String, abstractJSON> getMap() {
		return map;
	}

	//-----------------------------------------Extra Getter and Setter------------------------------
	public boolean isJSONValue(String key) {
		abstractJSON json = get(key);
		return json instanceof JSONValue;
	}
	
	public boolean isString(String key) {
		abstractJSON json = get(key);
		if(!(json instanceof JSONValue))
			return false;
		return ((JSONValue) json).isString();
	}
	
	public String getString(String key) {
		if (isJSONValue(key))
			return ((JSONValue) get(key)).getString();
		return null;
	}
	
	public boolean isBoolean(String key) {
		abstractJSON json = get(key);
		if(!(json instanceof JSONValue))
			return false;
		return ((JSONValue) json).isBoolean();
	}

	public Boolean getBoolean(String key) {
		if (isBoolean(key))
			return ((JSONValue) get(key)).getBoolean();
		return false;
	}

	public boolean isInt(String key) {
		abstractJSON json = get(key);
		if (json instanceof JSONValue)
			return ((JSONValue) json).isInt();
		return false;
	}
	
	public int getInt(String key) {
		abstractJSON json = get(key);
		if (json instanceof JSONValue)
			return ((JSONValue) json).getInt();
		return 0;
	}
	
	public boolean isLong(String key) {
		abstractJSON json = get(key);
		if (json instanceof JSONValue)
			return ((JSONValue) json).isLong();
		return false;
	}

	public long getLong(String key) {
		abstractJSON json = get(key);
		if (json instanceof JSONValue)
			return ((JSONValue) json).getLong();
		return 0;
	}
	
	public boolean isDouble(String key) {
		abstractJSON json = get(key);
		if (json instanceof JSONValue)
			return ((JSONValue) json).isDouble();
		return false;
	}

	public double getDouble(String key) {
		abstractJSON json = get(key);
		if (json instanceof JSONValue)
			return ((JSONValue) json).getDouble();
		return 0;
	}
	
	public boolean isFloat(String key) {
		abstractJSON json = get(key);
		if (json instanceof JSONValue)
			return ((JSONValue) json).isFloat();
		return false;
	}

	public float getFloat(String key) {
		abstractJSON json = get(key);
		if (json instanceof JSONValue)
			return ((JSONValue) json).getFloat();
		return 0;
	}
	//-----------------------------------------Until here------------------------------

	@Override
	protected void write(JSONWriter writer, int height, boolean shouldUseSpace) throws IOException {
		writer.write("{", height);
		writer.newLine();

		boolean first = true;

		for (String key : map.keySet()) {
			if (first) first = false;
			else {
				writer.write(",", 0);
				writer.newLine();
			}

			writer.write("\"" + key + "\"", height + 1);
			writer.write(':', 0);
			map.get(key).write(writer, height + 1, false);
		}

		writer.newLine();
		writer.write("}", height);
	}

	@Override
	protected StringBuffer toJSONString(StringBuffer buffer) {
		buffer.append("{");

		boolean first = true;

		for (String key : map.keySet()) {
			if (first) first = false;
			else {
				buffer.append(",");
			}

			buffer.append("\"").append(key).append("\":");
			map.get(key).toJSONString(buffer);
		}

		buffer.append("}");
		return buffer;
	}


}
