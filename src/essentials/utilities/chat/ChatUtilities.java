package essentials.utilities.chat;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import components.json.JSONArray;
import components.json.JSONObject;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;

public class ChatUtilities {
	public static void sendChatMessage(Player player, String message, JSONArray array) {
		JSONObject mainJson = new JSONObject();
		mainJson.add("text", message);
		mainJson.add("extra", array);
		
		IChatBaseComponent chat = ChatSerializer.a(mainJson.toJSONString());
		PacketPlayOutChat packet = new PacketPlayOutChat(chat);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
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
}
