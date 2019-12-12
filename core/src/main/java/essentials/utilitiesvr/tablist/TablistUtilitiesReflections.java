package essentials.utilitiesvr.tablist;

import components.reflections.SimpleReflection;
import essentials.utilities.minecraft.ReflectionsUtilities;
import essentials.utilitiesvr.chat.ChatUtilitiesReflections;
import essentials.utilitiesvr.player.PlayerUtilitiesReflections;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/*
 * Not being used -> Player#setPlayerListHeaderFooter
 */
public class TablistUtilitiesReflections {
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
