package essentials.timer;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import essentials.main.Main;

public class BukkitTimer {
	
	private TimerPosition position;
	private int ID = -1;
	private boolean UpOrDown = true; // UP = true, Down = false
	private boolean repeat = false;
	
	private int maxValue = 60; //1 Per Second
	private int currentValue = 0;
	private Runnable onFinished;
	private Runnable onEverySecond;
	
	//Bossbar Information if needed
	private String title;
	private BossBar bossBar;
	private BarColor color;
	private BarStyle style;
	private BarFlag[] barFlags;
	
	public BukkitTimer(TimerPosition position) {
		this.position = position;
	}
	
	public synchronized void start() {
		if(ID >= 0) return;
		
		switch (position) {
			case BOSSBAR:
				if(style == null)
					style = BarStyle.SEGMENTED_10;
				
				if(color == null)
					color = BarColor.WHITE;
				
				if(title == null)
					title = "";
				
				if(barFlags == null)
					barFlags = new BarFlag[0];
				
				bossBar = Bukkit.getServer().createBossBar(title, color, style, barFlags);
				
				for(Player player : Bukkit.getOnlinePlayers())
					bossBar.addPlayer(player); //TODO Join Event
				
				bossBar.setVisible(true);
				
				break;
			case CHAT:
				break;
			case NOWHERE:
				break;
		}
		
		ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
			if(UpOrDown) {
				currentValue++;
				
				if(currentValue > maxValue) {
					stop();
					if(onFinished != null)
						onFinished.run();
					return;
				}
			} else {
				currentValue--;
				
				if(currentValue <= 0) {
					stop();
					if(onFinished != null)
						onFinished.run();
					return;
				}
			}
			
			if(onEverySecond != null)
				onEverySecond.run();
			
			switch (position) {
				case BOSSBAR:
					bossBar.setProgress((1d / maxValue) * currentValue);
					break;
				case CHAT:
					Bukkit.broadcastMessage(currentValue + "");
					break;
				default:
					break;
			}
		}, 0l, 20l);
	}
	
	public synchronized void stop() {
		if(ID < 0) return;
		
		if(bossBar != null) {
			bossBar.setVisible(false);
		}
		
		Bukkit.getScheduler().cancelTask(ID);
		ID = -1;
	}

	public void setCountUp() {
		UpOrDown = true;
		currentValue = 0;
	}
	
	public boolean isCountUp() {
		return UpOrDown;
	}
	
	public void setCountDown() {
		UpOrDown = false;
		currentValue = maxValue;
	}
	
	public boolean isCountDown() {
		return !UpOrDown;
	}

	public TimerPosition getPosition() {
		return position;
	}

	public void setPosition(TimerPosition position) {
		this.position = position;
	}

	public BossBar getBossBar() {
		return bossBar;
	}

	public void setBossBar(BossBar bossBar) {
		this.bossBar = bossBar;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BarColor getColor() {
		return color;
	}

	public void setColor(BarColor color) {
		this.color = color;
	}

	public BarStyle getStyle() {
		return style;
	}

	public void setStyle(BarStyle style) {
		this.style = style;
	}

	public BarFlag[] getBarFlags() {
		return barFlags;
	}

	public void setBarFlags(BarFlag[] barFlags) {
		this.barFlags = barFlags;
	}

	public int getID() {
		return ID;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
}
