package essentials.utilitiesvr.tablist;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import components.reflections.SimpleReflection;
import essentials.utilitiesvr.ReflectionsUtilities;
import essentials.utilitiesvr.chat.ChatUtilitiesReflections;
import essentials.utilitiesvr.player.PlayerUtilitiesReflections;

public class TablistUtilitiesReflections {
	public static void sendHeaderFooter(String header, String footer) {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers())
			sendHeaderFooter(onlinePlayer, header, footer);
	}
	
	public static void sendHeaderFooter(Player player, String header, String footer) {
		try {
			Object tabTitle = ChatUtilitiesReflections.getIChatBaseComponentA("{\"text\": \"" + header + "\"}");
			Object tabFoot = ChatUtilitiesReflections.getIChatBaseComponentA("{\"text\": \"" + header + "\"}");
			
			Object PacketPlayOutPlayerListHeaderFooter = SimpleReflection.createObject(ReflectionsUtilities.getMCClass("PacketPlayOutPlayerListHeaderFooter"));
			Field footerField = SimpleReflection.getField("footer", PacketPlayOutPlayerListHeaderFooter);
			Field headerField = SimpleReflection.getField("header", PacketPlayOutPlayerListHeaderFooter);
			
			footerField.set(PacketPlayOutPlayerListHeaderFooter, tabTitle);
			headerField.set(PacketPlayOutPlayerListHeaderFooter, tabFoot);
			PlayerUtilitiesReflections.sendPacket(player, PacketPlayOutPlayerListHeaderFooter);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException | ClassNotFoundException | InstantiationException e) {
			e.printStackTrace();
		}
	}
}
