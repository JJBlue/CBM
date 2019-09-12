package essentials.modules.world.time;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import essentials.main.Main;

public class TimeWorldManager {
	private static int taskID = -1;
	
	static TimeWorldValues defaultTWV;
	private static Map<World, TimeWorldValues> map = Collections.synchronizedMap(new HashMap<>());
	private static Map<World, BossBar> bossbars = Collections.synchronizedMap(new HashMap<>());
	
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
		map.put(world, timeWorldValues);
		startTimer();
	}
	
	public static void clear() {
		map.clear();
		stopTimer();
	}
	
	public static void refreshSleepBossbar(World world, int currentPlayer, int maxPlayer) {
		BossBar bossBar;
		
		if((bossBar = bossbars.get(world)) != null) {
			
		} else {
			bossBar = Bukkit.createBossBar("Player sleeping: ", BarColor.WHITE, BarStyle.SOLID, new BarFlag[0]);
			
			bossBar.setVisible(true);
			
			for(Player player : world.getPlayers())
				bossBar.addPlayer(player);
		}
		
		bossBar.setTitle("Player sleeping: " + currentPlayer + "/" + maxPlayer);
		bossBar.setProgress((1 / maxPlayer) * currentPlayer);
	}
	
	public static void addBossBarPlayer(World world, Player player) {
		BossBar bossBar = bossbars.get(world);
		if(bossBar == null) return;
		
		bossBar.addPlayer(player);
	}
	
	public static void removeBossBarPlayer(World world, Player player) {
		BossBar bossBar = bossbars.get(world);
		if(bossBar == null) return;
		
		bossBar.removePlayer(player);
	}
	
	public synchronized static void startTimer() {
		if(taskID != -1) return;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
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
						
						if((100d / g) * c < twv.getMinPlayerSleepingPercent()) {
							bossbars.remove(world);
							continue;
						}
						
						long playerFactor = g != 0 ? 1 * (int) ((twv.getSleepSpeedFactor() / g) * c) : 0;
						worldTime += playerFactor;
						
						if(twv.isUseBossBar())
							refreshSleepBossbar(world, c, g);
						
					} else if(bossbars.containsKey(world))
						bossbars.remove(world);
					
					world.setTime(worldTime);
				}
			}
		}, 0l, 1l);
	}
	
	public synchronized static void stopTimer() {
		if(taskID == -1) return;
		
		Bukkit.getScheduler().cancelTask(taskID);
		taskID = -1;
	}
}
