package essentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ColorListener implements Listener {
	@EventHandler
	private void Chat(AsyncPlayerChatEvent e){
		e.setMessage(Farben(e.getMessage()));
	}
	
	public static String Farben(String text){
		if(text.contains("&"))
			return text.replaceAll("&", "§").replaceAll("§ ", "& ");
		return text;
	}
}