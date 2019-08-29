package essentials.utilitiesvr.tablist;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_14_R1.PlayerConnection;

public class TablistUtilities_v1_14 {
	public static void sendHeaderFooter(String header, String footer) {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers())
			sendHeaderFooter(onlinePlayer, header, footer);
	}
	
	public static void sendHeaderFooter(Player player, String header, String footer) {
		PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter();
		headerPacket.footer = tabTitle;
		headerPacket.header = tabFoot;
		
		playerConnection.sendPacket(headerPacket);
	}
}
