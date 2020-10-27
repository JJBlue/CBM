package cbm.v1_16_R2.utilitiesvr.tablist;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R2.IChatBaseComponent;
import net.minecraft.server.v1_16_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_16_R2.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_16_R2.PlayerConnection;

/*
 * Not being used -> Player#setPlayerListHeaderFooter
 */
public class TablistUtilities_Impl {
	public void sendHeaderFooter(Player player, String header, String footer) {
		PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");

		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter();
		headerPacket.footer = tabTitle;
		headerPacket.header = tabFoot;

		playerConnection.sendPacket(headerPacket);
	}
}
