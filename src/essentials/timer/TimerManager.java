package essentials.timer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TimerManager {
	public final static Map<Integer, BukkitTimer> timers = Collections.synchronizedMap(new HashMap<>());
	
	public synchronized static void stopTimer(int id) {
		BukkitTimer bukkitTimer = timers.remove(id);
		if(bukkitTimer != null)
			bukkitTimer.stop();
	}
}
