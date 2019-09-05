package essentials.commands.trade;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import essentials.language.LanguageConfig;
import essentials.main.Main;
import essentials.utilities.chat.ChatUtilities;
import essentials.utilities.chat.ClickAction;

public class TradeManager {
	
	private TradeManager() {}
	
	private static int timeoutPerSecond = 30;
	private static Map<String, TradeInformation> tradesRequests = Collections.synchronizedMap(new HashMap<>());
	
	static {
		final List<String> delete = new LinkedList<>();
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getPlugin(), () -> {
			long time = System.currentTimeMillis() - timeoutPerSecond * 1000;
			
			tradesRequests.forEach((s, ti) -> {
				if(ti.getStartRequest() < time) {
					String[] players = s.split("§");
					
					Player player = Bukkit.getPlayer(players[0]);
					if(player != null)
						LanguageConfig.sendMessage(player, "trade.expired", players[1]);
					
					player = Bukkit.getPlayer(players[1]);
					if(player != null)
						LanguageConfig.sendMessage(player, "trade.expired", players[0]);
					
					delete.add(s);
				}
			});
			
			for(String s : delete)
				tradesRequests.remove(s);
			delete.clear();
		}, 0l, 20l);
	}
	
	public synchronized static void request(Player from, Player to) {
		if(from == null || to == null) return;
		if(from == to) return;
		
		String id = generateID(from, to);
		
		if(tradesRequests.containsKey(id)) {
			if(tradesRequests.get(id).getRequestSendFrom() != from) {
				tradesRequests.remove(id);
				TradeSystem.openTradeInventory(from, to);
			}
		} else {
			tradesRequests.put(id, new TradeInformation(System.currentTimeMillis(), from));
			
			ChatUtilities.sendChatMessage(to, LanguageConfig.getString("trade.sendTrade", from.getName()),
				ChatUtilities.createExtra(
					ChatUtilities.createClickHoverMessage(LanguageConfig.getString("trade.accept"), null, null, ClickAction.RUN_COMMAND, "/cbm trade " + from.getName())
				)
			);
			
			LanguageConfig.sendMessage(from, "trade.gotTrade", to.getName());
		}
	}
	
	private static String generateID(Player p1, Player p2) {
		if(p1.getName().compareTo(p2.getName()) < 0)
			return p1.getName() + "§" + p2.getName();
		return p2.getName() + "§" + p1.getName();
	}
	
	public synchronized static void removePlayer(Player player) {
		if(player == null) return;
		
		List<String> delete = new LinkedList<>();
		
		tradesRequests.forEach((s, l) -> {
			if(s.startsWith(player.getName() + "§") || s.endsWith("§" + player.getName()))
				delete.add(s);
		});
		
		for(String s : delete)
			tradesRequests.remove(s);
	}
}
