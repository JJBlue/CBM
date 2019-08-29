package essentials.utilitiesvr.tablist;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import essentials.Image.staticImage;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_14_R1.PacketDataSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerInfo.PlayerInfoData;
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_14_R1.PlayerConnection;

/*
 * Not being used -> Player#setPlayerListHeaderFooter
 */
public class TablistUtilities_v1_14 {
	public static void sendHeaderFooter(Player player, String header, String footer) {
		PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter();
		headerPacket.footer = tabTitle;
		headerPacket.header = tabFoot;
		
		playerConnection.sendPacket(headerPacket);
	}
	
//	public static void dsss() {
//		PacketPlayOutPlayerInfo packetPlayOutPlayerInfo;
//		List<PlayerInfoData> players = new LinkedList<>();
//		headerPacket.b(new PacketDataSerializer(bytebuf));
//		
//		Player player;
//		player.setplayerlist
//	}
}
