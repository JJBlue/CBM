package cbm.v1_19_R1.utilitiesvr.chat;

import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cbm.utilitiesvr.chat.ChatUtilities_Interface;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.TextComponentSerializer;

public class ChatUtilities_Impl implements ChatUtilities_Interface {
	@Override
	public void sendMessage(Player player, String json, cbm.utilitiesvr.chat.ChatMessageType type) {
		Gson gson = new GsonBuilder().registerTypeAdapter(TextComponent.class, new TextComponentSerializer()).create();
		TextComponent textComponent = gson.fromJson(json, TextComponent.class);
//		String legacy = BaseComponent.toLegacyText(textComponent);
		
		var chattype = switch (type) {
			case CHAT -> ChatMessageType.CHAT;
			case GAME_INFO -> ChatMessageType.ACTION_BAR;
			case SYSTEM -> ChatMessageType.SYSTEM;
			default -> throw new UnsupportedOperationException();
		};
		
		player.spigot().sendMessage(chattype, player.getUniqueId(), textComponent);
	}
}
