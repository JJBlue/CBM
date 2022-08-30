package cbm.v1_18_R2.utilitiesvr.chat;

import org.bukkit.entity.Player;

import cbm.utilitiesvr.chat.ChatUtilities_Interface;
import cbm.v1_18_R2.utilitiesvr.player.PlayerUtilities_Impl;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatBaseComponent.ChatSerializer;
import net.minecraft.network.protocol.game.PacketPlayOutChat;

public class ChatUtilities_Impl implements ChatUtilities_Interface {
	@Override
	public void sendMessage(Player player, String json, cbm.utilitiesvr.chat.ChatMessageType type) {
		IChatBaseComponent chat = ChatSerializer.a(json);
		
		ChatMessageType chattype = ChatMessageType.valueOf(type.name());
		
		PacketPlayOutChat packet = new PacketPlayOutChat(chat, chattype, player.getUniqueId());
		PlayerUtilities_Impl.getPlayerConnection(player).a(packet);
	}
}
