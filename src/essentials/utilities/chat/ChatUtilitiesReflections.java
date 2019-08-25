package essentials.utilities.chat;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;

import components.json.JSONArray;
import components.json.JSONObject;
import components.reflections.SimpleReflection;
import essentials.utilities.ReflectionsUtilities;

public class ChatUtilitiesReflections {
	/*
	 * usage:
	 * 			ChatUtilitiesReflections.sendChatMessage(p, "hello ",
	 *				ChatUtilities.createExtra(
	 *					ChatUtilities.createClickHoverMessage("§4[-]", HoverAction.SHOW_Text, "Remove Command", ClickAction.RUN_COMMAND, "/cos remove hello"),
	 *					...
	 *				)
	 *			);
	 */
	public static void sendChatMessage(Player player, String message, JSONArray array) {
		JSONObject mainJson = new JSONObject();
		mainJson.add("text", message);
		mainJson.add("extra", array);
		
		ClassLoader classLoader = ChatUtilitiesReflections.class.getClassLoader();
		
		try {
			Object chat = getIChatBaseComponent(mainJson.toJSONString());
			Object packetPlayOutChat = SimpleReflection.createObject(classLoader.loadClass("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".PacketPlayOutChat"), chat);
			sendPacket(player, packetPlayOutChat);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public static JSONObject createClickHoverMessage(String hoverbalmessage, HoverAction hoverAction, String hovertext, ClickAction clickAction, String command) {
		JSONObject mainJson = new JSONObject();
		mainJson.add("text", hoverbalmessage);
		
		if(hovertext != null && hoverAction != null) {
			JSONObject hoverEvent = new JSONObject();
			hoverEvent.add("action", hoverAction.toString());
			hoverEvent.add("value", hovertext);
			
			mainJson.add("hoverEvent", hoverEvent);
		}
		
		if(command != null && clickAction != null) {
			JSONObject clickEvent = new JSONObject();
			clickEvent.add("action", clickAction.toString());
			clickEvent.add("value", command);
			
			mainJson.add("clickEvent", clickEvent);
		}
		
		return mainJson;
	}
	
	public static JSONArray createExtra(JSONObject... clickHoverMessage) {
		List<JSONObject> list = new LinkedList<>();
		
		for(JSONObject object : clickHoverMessage)
			list.add(object);
		
		return new JSONArray(list);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sendHotbarMessage(Player player, String message) {
        try {
        	Object IChatBaseComponent = getIChatBaseComponent("{\"text\": \"" + message + "\"}");
        	Enum ChatMessageType = SimpleReflection.getEnum((Class<Enum>) Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".ChatMessageType"), "GAME_INFO");
        	Object packetPlayOutChat = SimpleReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".PacketPlayOutChat"), IChatBaseComponent, ChatMessageType);
        	
			sendPacket(player, packetPlayOutChat);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException | ClassNotFoundException | InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	private static Object getIChatBaseComponent(String text) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> IChatBaseComponent = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".IChatBaseComponent$ChatSerializer", false, ChatUtilitiesReflections.class.getClassLoader());
		return SimpleReflection.callStaticMethod(IChatBaseComponent, "a", text);
	}
	
	private static void sendPacket(Player player, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchFieldException {
		Object playerConnection = SimpleReflection.getObject("playerConnection", SimpleReflection.callMethod(player, "getHandle"));
		SimpleReflection.callMethod(playerConnection, "sendPacket", obj);
	}
}
