package cbm.v1_16_R3.utilitiesvr.chat;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import cbm.utilitiesvr.chat.ChatUtilities_Interface;
import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;

public class ChatUtilities_Impl implements ChatUtilities_Interface {
	@Override
	public void sendMessage(Player player, String json, cbm.utilitiesvr.chat.ChatMessageType type) {
		IChatBaseComponent chat = ChatSerializer.a(json);
		
		ChatMessageType chattype = ChatMessageType.valueOf(type.name());
		
		PacketPlayOutChat packet = new PacketPlayOutChat(chat, chattype, player.getUniqueId());
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}
