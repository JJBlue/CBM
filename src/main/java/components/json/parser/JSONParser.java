package components.json.parser;

import components.json.JSONArray;
import components.json.JSONObject;
import components.json.JSONValue;
import components.json.abstractJSON;

import java.util.LinkedList;
import java.util.List;

public class JSONParser {
	public static abstractJSON parse(String json) {
		if(json == null) return null;
		return parser(removeSpaces(json));
	}
	
	private static String removeSpaces(String text) {
		if(text == null) return null;
		
		boolean anfuerungszeichen = false;
		boolean slash = false;
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			
			switch(c) {
				case'\\':
					slash = !slash;
					builder.append(c);
					break;
				case '"':
					if(!slash)anfuerungszeichen = !anfuerungszeichen;
					else slash = false;
					
					builder.append(c);
					break;
				default:
					if(slash)slash = false;
					if(anfuerungszeichen || !Character.isWhitespace(c))builder.append(c);
			}
		}
		
		return builder.toString();
	}
	
	private static abstractJSON parser(String me) {
		if(me == null) return null;
		me = me.trim();
		
		if(me.equals(""))return null;
		if(me.equals("[]"))return new JSONArray();
		if(me.equals("{}"))return new JSONObject();
		if(me.startsWith("\"") && me.endsWith("\""))return new JSONValue(me.substring(1, me.length()-1));
		if(!me.contains("{") && !me.contains("[")) {
			if(me.contains("}") || me.contains("}"))throw new IllegalArgumentException("Brackets missing -1");
			
			if(me.equalsIgnoreCase("null"))return new JSONValue(null);
			if(me.equalsIgnoreCase("true"))return new JSONValue(true);
			if(me.equalsIgnoreCase("false"))return new JSONValue(false);
			return new JSONValue(arrayHelper(me));
		}
		
		if(me.startsWith("[")) {
			JSONArray array = new JSONArray();
			List<String> content = splitter(me);
			
			List<Object> list = new LinkedList<>();
			for(String s : content) {
				if(s.startsWith("{") || s.startsWith("["))
					list.add(parser(s));
				else
					list.add(arrayHelper(s));
			}
			
			array.setArray(list);
			
			return array;
		}
		
		if(me.startsWith("{")) {
			JSONObject map = new JSONObject();
			List<String> content = splitter(me);
			
			String key = null;
			for(String keyAndValue : content) {
				if(!keyAndValue.startsWith("\""))throw new IllegalArgumentException("Anfuehrungszeichen fehlt 1");
				int lastPos = 0;
				
				boolean slash = false;
				boolean closeAnf = false;
				//search key
				for(int i = 1; i < keyAndValue.length(); i++) {
					boolean finished = false;
					
					switch(keyAndValue.charAt(i)) {
						case '\\':
							if(closeAnf)throw new IllegalArgumentException("Missing :");
							if(!slash)slash = true;
							break;
						case '"':
							if(!slash)closeAnf = true;
							break;
						case ':':
							if(closeAnf) {
								key = keyAndValue.substring(1, i-1);
								lastPos = i+1;
								finished = true;
							}
							break;
						default:
							if(closeAnf)throw new IllegalArgumentException("Missing :");
							if(slash)slash = false;
					}
					
					if(finished)break;
				}
				
				if(key == null)throw new IllegalArgumentException("Key missing 0");
				
				//Add to Map
				abstractJSON ajson = parser(keyAndValue.substring(lastPos, keyAndValue.length()));
				map.add(key, ajson);
			}
			
			return map;
		}
		
		return null;
	}
	
	private static Object arrayHelper(String me) {
		if(me.startsWith("\"") && me.endsWith("\""))return me.substring(1, me.length() - 1);
		
		if(me.contains("."))return Double.parseDouble(me);
		return Long.parseLong(me);
	}
	
	/*
	 * Splits by , only for the right list
	 * 
	 * Example:
	 * {t, {t, t}, t} ->	t	{t,t}	t
	 */
	private static List<String> splitter(String text) {
		int openRectangle = 0;
		int openBracket = 0;
		boolean anfuehrungszeichen = false;
		int lastPosition = 0;
		List<String> contents = new LinkedList<>();
		
		if(text.startsWith("[") && text.endsWith("]"));
		else if(text.startsWith("{") && text.endsWith("}"));
		else throw new IllegalArgumentException("Bracket missing 0");
		
		text = text.substring(1, text.length() - 1);
		
		if(text.contains(",")) {
			for(int i = 0; i < text.length(); i++) {
				switch(text.charAt(i)) {
					case '[':
						openRectangle++;
						break;
					case ']':
						openRectangle--;
						if(openRectangle < 0) throw new IllegalArgumentException("Brackets missing 1");
						break;
					case '{':
						openBracket++;
						break;
					case '}':
						openBracket--;
						if(openRectangle < 0) throw new IllegalArgumentException("Brackets missing 2");
						break;
					case '"':
						anfuehrungszeichen = !anfuehrungszeichen;
						break;
					case ',':
						if(openRectangle == 0 && openBracket == 0 && !anfuehrungszeichen) {
							contents.add(text.substring(lastPosition, i));
							lastPosition = i+1;
						}
				}
			}
			
			contents.add(text.substring(lastPosition, text.length()));
		}else if(text.length() > 0) {
			contents.add(text);
		}
		return contents;
	}
}
