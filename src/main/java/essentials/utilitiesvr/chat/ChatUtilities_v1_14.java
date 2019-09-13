package essentials.utilitiesvr.chat;

import components.json.JSONArray;
import components.json.JSONObject;
import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ChatUtilities_v1_14 {
	public static void sendChatMessage(Player player, String message, JSONArray array) {
		JSONObject mainJson = new JSONObject();
		mainJson.add("text", message);
		mainJson.add("extra", array);

		IChatBaseComponent chat = ChatSerializer.a(mainJson.toJSONString());
		PacketPlayOutChat packet = new PacketPlayOutChat(chat);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public static void sendHotbarMessage(Player player, String message) {
		IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + message + "\"}"));
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
	}
}
