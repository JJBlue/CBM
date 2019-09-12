package essentials.modules.world.time;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import essentials.main.Main;

public class TimeWorldManager {
	private static int taskID = -1;
	
	static TimeWorldValues defaultTWV; //TODO
	private static Map<World, TimeWorldValues> map = new HashMap<>();
	private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
	
	/*
	 * 	DAY:		1 000
	 * 	noon:		6 000
	 * 	night:		13 000
	 *  Midnight:	18 000
	 *  
	 *  Complete Day: 24000
	 */
	
	/*
	 * 	Real-Time
	 * 	Skip-Night (Percent of each Player), default 20
	 * 	Speed-Time (Factor), default 50
	 */
	
	public static void addSlepSpeed(World world, double factor, int minPlayer) {
		TimeWorldValues twv = new TimeWorldValues();
		twv.setMinPlayerSleepingPercent(minPlayer > 100 || minPlayer < 0 ? 0 : minPlayer);
		twv.setSpeedFactor(factor);
		
		addWorld(world, twv);
		startTimer();
	}

	public static void addRealTime(World world) {
		TimeWorldValues twv = new TimeWorldValues();
		twv.setUseRealTime(true);
		addWorld(world, twv);
		startTimer();
	}
	
	public static void addTimeSpeed(World world, double factor) {
		TimeWorldValues twv = new TimeWorldValues();
		twv.setSpeedFactor(factor);
		addWorld(world, twv);
		startTimer();
	}
	
	public static void setRealTime(World world) {
		world.setTime(getRealTime());
	}
	
	public static long getRealTime() {
		LocalTime localTime = LocalTime.now();
		
		int hours = localTime.getHour();
		int minute = localTime.getMinute();
		int seconds = localTime.getSecond();
		
		/*
		 * 0 = 18000
		 * 6 uhr = 0 ticks
		 * 7 = 1000
		 * 8 = 2000
		 * 12 = 6000
		 */
		
		int mc_time;
		
		if(hours < 6)
			mc_time = 18_000 + hours * 1_000;
		else
			mc_time = hours * 1_000 - 6_000;
		
		double tps = 1000 / 3600;
		
		mc_time += tps * ((minute * 60) + seconds);
		
		return mc_time;
	}
	
	public static void addWorld(World world, TimeWorldValues timeWorldValues) {
		readWriteLock.writeLock().lock();
		map.put(world, timeWorldValues);
		readWriteLock.writeLock().unlock();
	}
	
	public static void clear() {
		readWriteLock.writeLock().lock();
		map.clear();
		readWriteLock.writeLock().unlock();
	}
	
	public synchronized static void startTimer() {
		if(taskID != -1) return;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
			readWriteLock.readLock().lock();
			
			for(World world : Bukkit.getWorlds()) {
				TimeWorldValues twv = map.get(world);
				if(twv == null) twv = defaultTWV;
				if(twv == null) continue;
				
				if(twv.isUseRealTime())
					world.setTime(getRealTime());
				else {
					
					boolean isDay = world.getTime() > 1_000 && world.getTime() < 13_000;
					long worldTime = world.getTime() - 1;
					
					if(isDay)
						worldTime += (1 * twv.getDaySpeedFactor());
					else
						worldTime += (1 * twv.getNightSpeedFactor());
					
					//Sleep-Factor
					if(!isDay && twv.getSleepSpeedFactor() != 1) {
						int c = 0;
						int g = 0;
						for(Player player : world.getPlayers()) {
							g++;
							
							if(player.isSleeping())
								c++;
						}
						
						if((100 / g) * c < twv.getMinPlayerSleepingPercent()) continue;
						
						long playerFactor = g != 0 ? 1 * (int) ((twv.getSleepSpeedFactor() / g) * c) : 0;
						worldTime += playerFactor;
					}
					
					world.setTime(worldTime);
				}
			}
			
			readWriteLock.readLock().unlock();
		}, 0l, 1l);
	}
	
	public synchronized static void stopTimer() {
		if(taskID == -1) return;
		
		Bukkit.getScheduler().cancelTask(taskID);
		taskID = -1;
	}
}
