package essentials.modules.disguise.gameprofile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

import components.json.JSONArray;
import components.json.JSONObject;
import components.json.parser.JSONParser;

public class GameProfileBuilder {
	
	/*
	 	{
		    "id": "<ID>",
		    "name": "<Name>",
		    "properties": [
		        {
		            "name": "textures",
		            "value": "cmUvNDQyYzRiNDE4NzQ5ODE3MTM3YmY3YjE2ZDhjMjMwZjdiY2Y2OTgzYTQyMjYxY2MxYjkzYTVmYmJlODgyZGM3NiJ9fX0=",
		            "signature": "M5o6MjE/jfjppJk6SUv/2BhIxZWrRCuNoaqXBzdb6IsvaRl+MuJV7wjYeJq25XBTMB4k="
		        }
		    ]
		}
		
		value:
		
		{
		    "timestamp": 1569681644139,
		    "profileId": "<ID>",
		    "profileName": "<Name>",
		    "signatureRequired": true,
		    "textures": {
		        "SKIN": {
		            "url": "<URL/Texture>"
		        }
		    }
		}
	 */
	
	private static final String SERVICE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";
	private static final String JSON_SKIN = "{\"timestamp\":%d,\"profileId\":\"%s\",\"profileName\":\"%s\",\"isPublic\":true,\"textures\":{\"SKIN\":{\"url\":\"%s\"}}}";
	private static final String JSON_CAPE = "{\"timestamp\":%d,\"profileId\":\"%s\",\"profileName\":\"%s\",\"isPublic\":true,\"textures\":{\"SKIN\":{\"url\":\"%s\"},\"CAPE\":{\"url\":\"%s\"}}}";
	
	private static Map<UUID, CachedProfile> cache = Collections.synchronizedMap(new HashMap<UUID, CachedProfile>());
	static long cacheTime = 60_000 * 60; //In Millis; 60_000 * 60 = 1h
	
	public static void setCacheTime(long time) {
		cacheTime = time;
	}
	
	public static void checkCache() {
		cache.values().removeIf(cp -> !cp.isValid());
	}
	
	public static GameProfile fetch(UUID uuid) throws IOException {
		return fetch(uuid, false);
	}
	
	public static GameProfile fetch(UUID uuid, boolean forceNew) throws IOException {
		return fetch(uuid, null, null, forceNew);
	}
	
	public static GameProfile fetch(UUID uuid, UUID setUUID, String name, boolean forceNew) throws IOException {
		if (!forceNew && cache.containsKey(uuid) && cache.get(uuid).isValid())
			return setProfile(cache.get(uuid).profile, setUUID, name);
		else {
			String url = String.format(SERVICE_URL, UUIDTypeAdapter.fromUUID(uuid));
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setReadTimeout(5000);
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				checkCache();
				String json = readInput(connection.getInputStream());
				
				GameProfile result = deserialize(json, setUUID, name);
				cache.put(uuid, new CachedProfile(result));
				return result;
			} else {
				if (!forceNew && cache.containsKey(uuid))
						return setProfile(cache.get(uuid).profile, setUUID, name);
				
				JSONObject error = (JSONObject) JSONParser.parse(readInput(connection.getErrorStream()));
				throw new IOException(error.getString("error") + ": " + error.getString("errorMessage"));
			}
		}
	}
	
	protected static String readInput(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		StringBuilder json = new StringBuilder();
		String line;
		
		while((line = bufferedReader.readLine()) != null)
			json.append(line);
		
		return json.toString();
	}
	
	public static GameProfile setProfile(GameProfile oldGameProfile, UUID newUUID, String name) {
		if(newUUID == null && name == null) return oldGameProfile;
		GameProfile gameProfile = new GameProfile(newUUID != null ? newUUID : oldGameProfile.getId(), name != null ? name : oldGameProfile.getName());
		gameProfile.getProperties().putAll(oldGameProfile.getProperties()); //TODO Here is not a clone of the properties, so do not change skin or other things
		return gameProfile;
	}
	
	public static GameProfile getProfile(UUID uuid, String name, String skin) {
		return getProfile(uuid, name, skin, null);
	}
	
	public static GameProfile getProfile(UUID uuid, String name, String skinUrl, String capeUrl) {
		GameProfile profile = new GameProfile(uuid, name);
		boolean cape = capeUrl != null && !capeUrl.isEmpty();
		
		List<Object> args = new ArrayList<Object>();
		args.add(System.currentTimeMillis());
		args.add(UUIDTypeAdapter.fromUUID(uuid));
		args.add(name);
		args.add(skinUrl);
		if (cape) args.add(capeUrl);
		
		profile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString(String.format(cape ? JSON_CAPE : JSON_SKIN, args.toArray(new Object[args.size()])))));
		return profile;
	}
	
	public static GameProfile deserialize(String jsonString) {
		return deserialize(jsonString, null, null);
	}
	
	public static GameProfile deserialize(String jsonString, UUID setUUID, String setName) {
		JSONObject json = (JSONObject) JSONParser.parse(jsonString);
		GameProfile profile = new GameProfile(setUUID != null ? setUUID : UUIDTypeAdapter.fromString(json.getString("id")), setName != null ? setName : json.getString("name"));
		
		JSONArray properties = (JSONArray) json.get("properties");
		
		for(Object json_object : properties.getList()) {
			JSONObject json_property = (JSONObject) json_object;
			
			String name = json_property.getString("name");
			Property property = new Property(name, json_property.getString("value"), json_property.getString("signature"));
			profile.getProperties().put(name, property);
		}
		
		return profile;
	}
	
	public static String serialize(GameProfile profile) {
		JSONObject json = new JSONObject();
		json.add("id", UUIDTypeAdapter.fromUUID(profile.getId()));
		json.add("name", profile.getName());
		
		List<Property> list = new LinkedList<>();
		JSONArray properties = new JSONArray(list);
		json.add("properties", properties);
		
		for(Property property : profile.getProperties().values()) {
			JSONObject json_property = new JSONObject();
			json_property.add("name", property.getName());
			json_property.add("value", property.getValue());
			json_property.add("signature", property.getValue());
			list.add(property);
		}
		
		return json.toJSONString();
	}
}