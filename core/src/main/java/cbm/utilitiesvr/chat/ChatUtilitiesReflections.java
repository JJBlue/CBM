package cbm.utilitiesvr.chat;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import cbm.utilitiesvr.player.PlayerUtilitiesReflections;
import cbm.versions.minecraft.ReflectionsUtilities;
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
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void sendMessage(Player player, String json, ChatMessageType type) { // TODO changed since 1.16
		try {
			Object chat = getIChatBaseComponentA(json);
			Enum chatMessageType = null;
			
			switch (type) {
				case CHAT:
					chatMessageType = ObjectReflection.getEnum((Class<Enum>) ReflectionsUtilities.getMCClass("ChatMessageType"), "CHAT");
					break;
				case GAME_INFO:
					chatMessageType = ObjectReflection.getEnum((Class<Enum>) ReflectionsUtilities.getMCClass("ChatMessageType"), "GAME_INFO");
					break;
				case SYSTEM:
					chatMessageType = ObjectReflection.getEnum((Class<Enum>) ReflectionsUtilities.getMCClass("ChatMessageType"), "SYSTEM");
					break;
			}
			
			Object packetPlayOutChat = ConstructorReflection.createObject(ReflectionsUtilities.getMCClass("PacketPlayOutChat"), chat, chatMessageType);
			PlayerUtilitiesReflections.sendPacket(player, packetPlayOutChat);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public static Object getIChatBaseComponentA(String text) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> IChatBaseComponent = ReflectionsUtilities.getMCClass("IChatBaseComponent$ChatSerializer");
		return MethodReflection.callStaticMethod(IChatBaseComponent, "a", text);
	}
}
