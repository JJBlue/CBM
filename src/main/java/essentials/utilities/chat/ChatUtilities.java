package essentials.utilities.chat;

import components.json.JSONArray;
import components.json.JSONObject;
import essentials.utilitiesvr.chat.ChatUtilitiesReflections;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ChatUtilities {
	public static String convertToColor(String text) {
		return text.replaceAll("&(\\d|\\w)", "§$1");
	}
	
	public static void sendChatMessage(Player player, String message, JSONArray array) {
		ChatUtilitiesReflections.sendChatMessage(player, message, array);
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

		list.addAll(Arrays.asList(clickHoverMessage));
		
		return new JSONArray(list);
	}
	
	public static void sendHotbarMessage(Player player, String message) {
		ChatUtilitiesReflections.sendHotbarMessage(player, message);
	}
}
