package components.json;

import java.io.IOException;
import java.util.List;

public class JSONValue extends abstractJSON{
	
	/*
	 * String
	 * Number
	 * Object
	 * array
	 * true
	 * false
	 * null
	 */
	private Object value;
	
	public JSONValue(Object value) {
		setValue(value);
	}
	
	public static abstractJSON getabstractJSON(Object value) {
		if(value instanceof abstractJSON)return (abstractJSON) value;
		
		if(value instanceof List<?>)
			return new JSONArray((List<?>) value);
		
		return new JSONValue(value);
	}
	
	public void setValue(Object v) {
		if(v == null)value = null;
		else if(v instanceof String || v instanceof Double || v instanceof Integer || v instanceof Long || v instanceof Float) {
			value = v;
		}else if(v instanceof Boolean) {
			value = v;
		}else if(v instanceof List<?>) {
			this.value = new JSONArray((List<?>) v);
		}else{
			//Object
		}
	}
	
	public Object getValue() {
		return value;
	}
	
	public String getString() {
		return value.toString();
	}
	
	public boolean getBoolean() {
		if(value instanceof Boolean)return (boolean) value;
		return false;
	}
	
	public int getInt() {
		if(value instanceof Integer)return (int) value;
		return 0;
	}
	
	public long getLong() {
		if(value instanceof Integer || value instanceof Long)return (long) value;
		return 0;
	}
	
	public float getFloat() {
		if(value instanceof Float)return (float) value;
		return 0;
	}
	
	public double getDouble() {
		if(value instanceof Double || value instanceof Float)return (double) value;
		return 0;
	}
	
	@Override
	protected void write(JSONWriter writer, int height, boolean shouldUseSpace) throws IOException {
		if(value == null)writer.write("null", shouldUseSpace ? height : 0);
		else if(value instanceof Boolean) {
			if((Boolean)value == true)writer.write("true", shouldUseSpace ? height : 0);
			else writer.write("false", shouldUseSpace ? height : 0);
		}
		else if(value instanceof JSONArray)((JSONArray)value).write(writer, height+1, true);
		else if(value instanceof String) {
			writer.write("\"" + escapeString((String) value) + "\"", shouldUseSpace ? height : 0);
		}else if(value instanceof Double || value instanceof Integer || value instanceof Long || value instanceof Float)
			writer.write(value + "", shouldUseSpace ? height : 0);
		else if(value instanceof JSONObject)
			((JSONObject) value).write(writer, height, true);
	}
	
	@Override
	protected StringBuffer toJSONString(StringBuffer buffer) {
		if(value == null)buffer.append("null");
		else if(value instanceof Boolean) {
			if((Boolean)value == true)buffer.append("true");
			else buffer.append("false");
		}
		else if(value instanceof JSONArray)((JSONArray)value).toJSONString(buffer);
		else if(value instanceof String) {
			buffer.append("\"" + escapeString((String) value) + "\"");
		}else if(value instanceof Double || value instanceof Integer || value instanceof Long || value instanceof Float)
			buffer.append(value + "");
		else if(value instanceof JSONObject)
			((JSONObject) value).toJSONString(buffer);
		return buffer;
	}
	
	public static String escapeString(String string) {
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			
			switch(c) {
				case '"':
					builder.append("\\\"");
					break;
				case '\\':
					builder.append("\\\\");
					break;
				case '\b':
					builder.append("\\b");
					break;
				case '\f':
					builder.append("\\f");
					break;
				case '\n':
					builder.append("\\n");
					break;
				case '\r':
					builder.append("\\r");
					break;
				case '\t':
					builder.append("\\t");
					break;
				default:
					builder.append(c);
			}
		}
		
		return builder.toString();
	}
}
