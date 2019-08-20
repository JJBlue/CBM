package essentials.utilities;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlayerUtilities {
	@SuppressWarnings("deprecation")
	public static OfflinePlayer getOfflinePlayer(String name) {
		return Bukkit.getOfflinePlayer(name);
	}
	
//	public static void sendChatMessage(Player p, String Text, HoverEvent.Action hoverAction, String HoverText, ClickEvent.Action action, String Command) {
//		TextComponent message = new TextComponent(Text);
//
//		if (Command != null)
//			message.setClickEvent(new ClickEvent(action, Command));
//		if (HoverText != null)
//			message.setHoverEvent(new HoverEvent(hoverAction, new ComponentBuilder(HoverText).create()));
//		
//		p.spigot().sendMessage(message);
//	}
	
//	public static void createClickHoverMessage(Player player, String message, String hoverbalmessage, String hover, String command) {
//		Bukkit.broadcastMessage("{\"text\":\"" + message + "\",\"extra\":[{\"text\":\"" + hoverbalmessage + "\",\"hoverEvent\":{\"action\":\"show_text\", \"value\":\"" + hover + "\"},\"clickEvent\":{\"action\":\"run_command\":\"value\":\"/" + command + "\"}}]}");
//		IChatBaseComponent chat = ChatSerializer.a("{\"text\":\"" + message + "\",\"extra\":[{\"text\":\"" + hoverbalmessage + "\",\"hoverEvent\":{\"action\":\"show_text\", \"value\":\"" + hover + "\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/" + command + "\"}}]}");
//		PacketPlayOutChat packet = new PacketPlayOutChat(chat);
//		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
//	}
}
