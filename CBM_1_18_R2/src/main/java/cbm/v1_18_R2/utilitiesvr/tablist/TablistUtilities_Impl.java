package cbm.v1_18_R2.utilitiesvr.tablist;

import org.bukkit.entity.Player;

import cbm.v1_18_R2.utilitiesvr.player.PlayerUtilities_Impl;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatBaseComponent.ChatSerializer;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.network.PlayerConnection;

/*
 * Not being used -> Player#setPlayerListHeaderFooter
 */
public class TablistUtilities_Impl {
	public void sendHeaderFooter(Player player, String header, String footer) {
		PlayerConnection playerConnection = PlayerUtilities_Impl.getPlayerConnection(player);
		IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");

		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle, tabFoot);

		playerConnection.a(headerPacket);
	}
}
