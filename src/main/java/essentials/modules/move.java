package essentials.modules;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class move implements Listener {
	
	private static Map<Player, LocalDateTime> standStill = new HashMap<>();
	private static int taskID;
	
	public synchronized static void start() {
		taskID = standStill
	}
	
	public void move(PlayerMoveEvent event) {
		
	}
}
