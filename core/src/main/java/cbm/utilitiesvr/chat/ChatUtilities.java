package cbm.utilitiesvr.chat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;

import cbm.versions.VersionDependency;
import cbm.versions.minecraft.MinecraftVersions;
import components.json.JSONArray;
import components.json.JSONObject;

public class ChatUtilities {
	public final static VersionDependency<ChatUtilities_Interface> version_dependency = new VersionDependency<>();
	
	public static void sendMessage(Player player, String json, ChatMessageType type) {
		ChatUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			vd.sendMessage(player, json, type);
		else
			ChatUtilitiesReflections.sendMessage(player, json, type);
	}
	
	public static String convertToColor(String text) {
		return text.replaceAll("&(\\d|\\w)", "§$1");
	}

	public static JSONObject createClickHoverMessage(String hoverbalmessage, HoverAction hoverAction, String hovertext, ClickAction clickAction, String command) {
		JSONObject mainJson = new JSONObject();
		mainJson.add("text", hoverbalmessage);

		if (hovertext != null && hoverAction != null) {
			JSONObject hoverEvent = new JSONObject();
			hoverEvent.add("action", hoverAction.toString());
			hoverEvent.add("value", hovertext);

			mainJson.add("hoverEvent", hoverEvent);
		}

		if (command != null && clickAction != null) {
			JSONObject clickEvent = new JSONObject();
			clickEvent.add("action", clickAction.toString());
			clickEvent.add("value", command);

			mainJson.add("clickEvent", clickEvent);
		}

		return mainJson;
	}
	
	public static String createMessage(String message) {
		JSONObject mainJson = new JSONObject();
		mainJson.add("text", message);
		return mainJson.toJSONString();
	}
	
	public static String createMessage(String message, JSONArray extra) {
		JSONObject mainJson = new JSONObject();
		mainJson.add("text", message);
		mainJson.add("extra", extra);
		return mainJson.toJSONString();
	}

	public static JSONArray createExtra(JSONObject... clickHoverMessage) {
		List<JSONObject> list = new LinkedList<>();
		list.addAll(Arrays.asList(clickHoverMessage));
		return new JSONArray(list);
	}
}
