package cbm.modules.display;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;

import cbm.main.Main;

public class DisplayBossBarTimer {
	
	public final static Map<BossBar, DisplayBossBarValues> map = Collections.synchronizedMap(new HashMap<>());
	private static int taskID = -1;
	
	public static void addBossbar(BossBar bossBar, long time, Runnable stopRunnable) {
		synchronized (map) {
			DisplayBossBarValues dbbv = map.get(bossBar);
			
			if(dbbv == null) {
				dbbv = new DisplayBossBarValues(bossBar);
				map.put(bossBar, dbbv);
			} else
				dbbv.startTime = System.currentTimeMillis();
			
			dbbv.time = time;
			dbbv.stopRunnable = stopRunnable;
		}
		
		startTimer();
	}
	
	public synchronized static void startTimer() {
		if(taskID > 0) return;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
			long currentTime = System.currentTimeMillis();
			
			synchronized (map) {
				Iterator<BossBar> iterator = map.keySet().iterator();
				
				while(iterator.hasNext()) {
					BossBar bossBar = iterator.next();
					DisplayBossBarValues dbbv = map.get(bossBar);
					
					if(currentTime - dbbv.startTime >= dbbv.time * 1000) {
						iterator.remove();
						if(dbbv.stopRunnable != null)
							dbbv.stopRunnable.run();
						bossBar.removeAll();
					}
				}
				
				if(map.isEmpty())
					stopTimer();
			}
		}, 0L, 20L);
	}
	
	public synchronized static void stopTimer() {
		if(taskID <= 0) return;
		
		Bukkit.getScheduler().cancelTask(taskID);
		taskID = -1;
	}
}
