package cbm.utilitiesvr.chat;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import cbm.utilitiesvr.player.PlayerUtilitiesReflections;
import cbm.versions.minecraft.ReflectionsUtilities;
import components.json.JSONArray;
import components.json.JSONObject;
import components.reflection.ConstructorReflection;
import components.reflection.MethodReflection;
import components.reflection.ObjectReflection;

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

		try {
			Object chat = getIChatBaseComponentA(mainJson.toJSONString());
			Object packetPlayOutChat = ConstructorReflection.createObject(ReflectionsUtilities.getMCClass("PacketPlayOutChat"), chat);
			PlayerUtilitiesReflections.sendPacket(player, packetPlayOutChat);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void sendHotbarMessage(Player player, String message) {
		try {
			Object IChatBaseComponent = getIChatBaseComponentA("{\"text\": \"" + message + "\"}");
			Enum ChatMessageType = ObjectReflection.getEnum((Class<Enum>) ReflectionsUtilities.getMCClass("ChatMessageType"), "GAME_INFO");
			Object packetPlayOutChat = ConstructorReflection.createObject(ReflectionsUtilities.getMCClass("PacketPlayOutChat"), IChatBaseComponent, ChatMessageType);

			PlayerUtilitiesReflections.sendPacket(player, packetPlayOutChat);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException | ClassNotFoundException | InstantiationException e) {
			e.printStackTrace();
		}
	}

	public static Object getIChatBaseComponentA(String text) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> IChatBaseComponent = ReflectionsUtilities.getMCClass("IChatBaseComponent$ChatSerializer");
		return MethodReflection.callStaticMethod(IChatBaseComponent, "a", text);
	}
}
