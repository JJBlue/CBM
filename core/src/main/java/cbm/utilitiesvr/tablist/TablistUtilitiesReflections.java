package cbm.utilitiesvr.tablist;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import cbm.utilitiesvr.chat.ChatUtilitiesReflections;
import cbm.utilitiesvr.player.PlayerUtilitiesReflections;
import cbm.versions.minecraft.ReflectionsUtilities;
import components.reflection.ConstructorReflection;
import components.reflection.ObjectReflection;

/*
 * Not being used -> Player#setPlayerListHeaderFooter
 */
public class TablistUtilitiesReflections {
	public static void sendHeaderFooter(Player player, String header, String footer) {
		try {
			Object tabTitle = ChatUtilitiesReflections.getIChatBaseComponentA("{\"text\": \"" + header + "\"}");
			Object tabFoot = ChatUtilitiesReflections.getIChatBaseComponentA("{\"text\": \"" + header + "\"}");

			Object PacketPlayOutPlayerListHeaderFooter = ConstructorReflection.createObject(ReflectionsUtilities.getMCClass("PacketPlayOutPlayerListHeaderFooter"));
			Field footerField = ObjectReflection.getField("footer", PacketPlayOutPlayerListHeaderFooter);
			Field headerField = ObjectReflection.getField("header", PacketPlayOutPlayerListHeaderFooter);

			footerField.set(PacketPlayOutPlayerListHeaderFooter, tabTitle);
			headerField.set(PacketPlayOutPlayerListHeaderFooter, tabFoot);
			PlayerUtilitiesReflections.sendPacket(player, PacketPlayOutPlayerListHeaderFooter);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException | ClassNotFoundException | InstantiationException e) {
			e.printStackTrace();
		}
	}
}
