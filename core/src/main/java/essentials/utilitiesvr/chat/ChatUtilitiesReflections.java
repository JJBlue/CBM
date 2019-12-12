package essentials.utilitiesvr.chat;

import components.json.JSONArray;
import components.json.JSONObject;
import components.reflections.SimpleReflection;
import essentials.utilities.minecraft.ReflectionsUtilities;
import essentials.utilitiesvr.player.PlayerUtilitiesReflections;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

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
			Object packetPlayOutChat = SimpleReflection.createObject(ReflectionsUtilities.getMCClass("PacketPlayOutChat"), chat);
			PlayerUtilitiesReflections.sendPacket(player, packetPlayOutChat);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void sendHotbarMessage(Player player, String message) {
		try {
			Object IChatBaseComponent = getIChatBaseComponentA("{\"text\": \"" + message + "\"}");
			Enum ChatMessageType = SimpleReflection.getEnum((Class<Enum>) ReflectionsUtilities.getMCClass("ChatMessageType"), "GAME_INFO");
			Object packetPlayOutChat = SimpleReflection.createObject(ReflectionsUtilities.getMCClass("PacketPlayOutChat"), IChatBaseComponent, ChatMessageType);

			PlayerUtilitiesReflections.sendPacket(player, packetPlayOutChat);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException | ClassNotFoundException | InstantiationException e) {
			e.printStackTrace();
		}
	}

	public static Object getIChatBaseComponentA(String text) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> IChatBaseComponent = ReflectionsUtilities.getMCClass("IChatBaseComponent$ChatSerializer");
		return SimpleReflection.callStaticMethod(IChatBaseComponent, "a", text);
	}
}
