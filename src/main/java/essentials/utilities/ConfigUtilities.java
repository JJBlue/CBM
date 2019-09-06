package essentials.utilities;

import components.json.JSONArray;
import components.json.JSONObject;
import components.json.JSONValue;
import components.json.abstractJSON;
import components.json.parser.JSONParser;
import components.reflections.SimpleReflection;
import essentials.utilitiesvr.ReflectionsUtilities;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConfigUtilities {
	private static Map<String, Class<?>> stringToClass;
	
	static {
		stringToClass = new HashMap<>();
		registerClass(Vector.class);
        registerClass(BlockVector.class);
        registerClass(ItemStack.class);
        registerClass(Color.class);
        registerClass(PotionEffect.class);
        registerClass(FireworkEffect.class);
        registerClass(Pattern.class);
        registerClass(Location.class);
        registerClass(AttributeModifier.class);
        registerClass(BoundingBox.class);
	}
	
	public static void registerClass(Class<?> classy) {
		stringToClass.put(classy.getSimpleName(), classy);
	}
	
	public static String toString(ConfigurationSerializable serializable) {
		return toJsonObject(serializable).toJSONString();
	}
	
	/*
	 * 	java.lang.Integer
		org.bukkit.craftbukkit.v1_14_R1.inventory.CraftMetaBlockState
		com.google.common.collect.SingletonImmutableList
		java.lang.String
		
		{
		    "meta": {
		        "meta-type": "TILE_ENTITY",
		        "internal": "H4sIAAAAAAAAAE1QsU7DMBS8NIloItQFIWCoFGVhpF0YMoKKVKlMdI9M7KRWHTuy3dIK8T39j35Z3aRAPN6d791dDMQYvQhVrGfScrtfkipCOLesNjGAgYfgQyiLIQac4q7mkhWalDajnNRK0tx8KU09hK9qI60Xw7ek8hG9acbOH40zcegV5aYRZB8hWCjNhg4N8PCdWrazaZYeD88LtmUiS46Hcp7+YNynZrumJSbJUzKdTBz92KfPp5J3RXnJmU7ao6186oT3PWH5qzGZYyJcz2SxItLWTNq2rDeAL7YCYVf25r+sWRHdSGYMfIRt0rM8gO+i4fJ8jObUWbUnlkqJDr4M6HWe456n1bxpGM1JQQpOcqGqvxkvc9/2E2zEmun8U+2AE6OAz7+2AQAA",
		        "blockMaterial": "SHULKER_BOX"
		    },
		    "type": "SHULKER_BOX"
		}
	 * 
	 * String
	 * Integer
	 * org.bukktit.craftbukkit.v1_14_R1.inventory.CraftMetaBlockState
	 * com.google.common.collect.SingletonImmutableList
	 */
	public static JSONObject toJsonObject(ConfigurationSerializable cs) {
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> map = cs.serialize();
		
		if(cs instanceof ItemStack)
			jsonObject.add("==", "ItemStack");
		else if(cs instanceof ItemMeta)
			jsonObject.add("==", "ItemMeta");
		else if(cs instanceof Pattern)
			jsonObject.add("==", "Pattern");
		else
			jsonObject.add("==", cs.getClass().getSimpleName());
		
		
		for(String key : map.keySet()) {
			Object value = map.get(key);
			
			if(value instanceof String || value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float)
				jsonObject.add(key, value);
			else if(value instanceof ConfigurationSerializable)
				jsonObject.add(key, toJsonObject((ConfigurationSerializable) value));
			else if(value instanceof List)
				jsonObject.add(key, toJsonArray((List<?>) value));
			else
				System.out.println("Missing Class! : " + value.getClass().getName());
		}
		
		return jsonObject;
	}
	
	public static JSONArray toJsonArray(List<?> list) {
		JSONArray array = new JSONArray();
		List<Object> l = new LinkedList<>();
		array.setArray(l);
		
		for(Object value : list) {
			if(value instanceof String || value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float)
				l.add(value);
			else if(value instanceof ConfigurationSerializable)
				l.add(toJsonObject((ConfigurationSerializable) value));
			else if(value instanceof List)
				l.add((List<?>) value);
			else
				System.out.println("Missing Class! : " + value.getClass().getName());
		}
		
		return array;
	}
	
	public static Object toObject(String json) {
		if(json == null) return null;
		return toObject(JSONParser.parse(json));
	}
	
	public static Object toObject(abstractJSON aJson) {
		if(aJson == null) return null;
		if(aJson instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) aJson;
			
			Map<String, Object> map = new HashMap<String, Object>();
			String className = null;
			
			for(String key : jsonObject.getMap().keySet()) {
				abstractJSON valueAJson = jsonObject.get(key);
				
				if(valueAJson instanceof JSONValue) {
					if(key.equalsIgnoreCase("=="))
						className = jsonObject.getString(key);
					else
						map.put(key, ((JSONValue) valueAJson).getValue());
				} else if(valueAJson instanceof JSONObject || valueAJson instanceof JSONArray)
					map.put(key, toObject(valueAJson));
			}
			
			if(className != null) {
				if(className.equalsIgnoreCase("ItemMeta")){
					try {
						Class<?> classy = Class.forName("org.bukkit.craftbukkit." + ReflectionsUtilities.getPackageVersionName() + ".inventory.CraftMetaItem$SerializableMeta");
						return SimpleReflection.callStaticMethod(classy, "deserialize", map);
					} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				} else if(className.equalsIgnoreCase("Pattern")) {
					return new Pattern(map);
				} else if(stringToClass.containsKey(className)) {
					try {
						return SimpleReflection.callStaticMethod(stringToClass.get(className), "deserialize", map);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			} else
				System.out.println("Missing Class! # 2");
			
		} else if(aJson instanceof JSONArray) {
			JSONArray array = (JSONArray) aJson;
			List<Object> list = new LinkedList<>();
			
			for(Object obj : array.getList()) {
				if(!(obj instanceof abstractJSON)) continue;
				abstractJSON valueAJson = (abstractJSON) obj;
				
				if(valueAJson instanceof JSONValue) {
					list.add(((JSONValue) valueAJson).getValue());
				} else if(valueAJson instanceof JSONObject || valueAJson instanceof JSONArray) {
					list.add(toObject(valueAJson));
				} else
					System.out.println("Missing Class! #4");
			}
			
			return list;
		}
		
		return null;
	}
	
	public static ItemStack toItemStack(Map<String, Object> map) {
		return ItemStack.deserialize(map);
	}
}
