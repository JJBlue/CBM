package cbm.modules.timer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class TimerManager {
	public final static Map<Integer, BukkitTimer> timers = Collections.synchronizedMap(new HashMap<>());

	public synchronized static void stopTimer(int id) {
		BukkitTimer bukkitTimer = timers.remove(id);
		if (bukkitTimer != null)
			bukkitTimer.stop();
	}

	public static void addPlayer(Player player) {
		synchronized (timers) {
			for (int i : timers.keySet()) {
				BukkitTimer bukkitTimer = timers.get(i);
				bukkitTimer.addPlayer(player);
			}
		}
	}
}
