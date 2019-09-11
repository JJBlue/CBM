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
	
	private static TimeWorldValues defaultTWV; //TODO
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
	 * 	Skip-Night (Percent of each Player)
	 * 	Speed-Time (Factor)
	 */
	
	
	public static void addSlepSpeed(World world, double factor, int minPlayer) {
		TimeWorldValues twv = new TimeWorldValues(TimeWorldEnum.SLEEP_SPEED);
		twv.setStartMinPlayerSleeping(minPlayer);
		twv.setSpeedFactor(factor);
		
		addWorld(world, twv);
		startTimer();
	}

	public static void addRealTime(World world) {
		TimeWorldValues twv = new TimeWorldValues(TimeWorldEnum.REAL_TIME);
		
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
		 * 0 = 18
		 * 1 = 19
		 * 2 = 20
		 * 3 = 21
		 * 4 = 22
		 * 5 = 23
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
	
	public synchronized static void startTimer() {
		if(taskID != -1) return;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
			readWriteLock.readLock().lock();
			
			for(World world : Bukkit.getWorlds()) {
				TimeWorldValues twv = map.get(world);
				if(twv == null) twv = defaultTWV;
				if(twv == null) continue;
				
				switch (twv.getTimeWorldEnum()) {
					case REAL_TIME:
						world.setTime(getRealTime());
						break;
					case SLEEP_SPEED:
						if(world.getTime() > 1_000 || world.getTime() < 13_000) continue;
						
						int c = 0;
						int g = 0;
						for(Player player : world.getPlayers()) {
							g++;
							
							if(player.isSleeping())
								c++;
						}
						
						if(c < twv.getStartMinPlayerSleeping())
							continue;
						
						double playerFactor = (twv.getSpeedFactor() / g) * c;
						
						world.setTime((long) (world.getTime() - 1 + (1d * playerFactor)));
						
						break;
					case SPEED_TIME:
						
						world.setTime((long) (world.getTime() - 1 + (1d * twv.getSpeedFactor())));
						
						break;
					default:
						break;
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
