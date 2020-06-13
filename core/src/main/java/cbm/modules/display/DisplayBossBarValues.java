package cbm.modules.display;

import org.bukkit.boss.BossBar;

public class DisplayBossBarValues {
	BossBar bossBar;
	long time; // How long the bossbar will be shown
	long startTime;
	Runnable stopRunnable;
	
	public DisplayBossBarValues(BossBar bossBar) {
		this.bossBar = bossBar;
		startTime = System.currentTimeMillis();
	}
	
	public DisplayBossBarValues(BossBar bossBar, long startTime) {
		this.bossBar = bossBar;
		this.startTime = startTime;
	}

	public BossBar getBossBar() {
		return bossBar;
	}

	public void setBossBar(BossBar bossBar) {
		this.bossBar = bossBar;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public Runnable getStopRunnable() {
		return stopRunnable;
	}

	public void setStopRunnable(Runnable stopRunnable) {
		this.stopRunnable = stopRunnable;
	}
}
