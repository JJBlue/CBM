package cbm.modules.world.time;

public class TimeWorldValues {
	private boolean useRealTime;

	private int minPlayerSleepingPercent;
	private double sleepSpeedFactor = 1;

	private double nightSpeedFactor = 1;
	private double daySpeedFactor = 1;

	private boolean useBossBar;

	public TimeWorldValues() {}

	public boolean isUseBossBar() {
		return useBossBar;
	}

	public void setUseBossBar(boolean useBossBar) {
		this.useBossBar = useBossBar;
	}

	public double getNightSpeedFactor() {
		return nightSpeedFactor;
	}

	public void setNightSpeedFactor(double nightSpeedFactor) {
		this.nightSpeedFactor = nightSpeedFactor;
	}

	public double getDaySpeedFactor() {
		return daySpeedFactor;
	}

	public void setDaySpeedFactor(double daySpeedFactor) {
		this.daySpeedFactor = daySpeedFactor;
	}

	public double getSleepSpeedFactor() {
		return sleepSpeedFactor;
	}

	public void setSleepSpeedFactor(double sleepSpeedFactor) {
		this.sleepSpeedFactor = sleepSpeedFactor;
	}

	public void setSpeedFactor(double factor) {
		nightSpeedFactor = factor;
		daySpeedFactor = factor;
	}

	public boolean isUseRealTime() {
		return useRealTime;
	}

	public void setUseRealTime(boolean useRealTime) {
		this.useRealTime = useRealTime;
	}

	public int getMinPlayerSleepingPercent() {
		return minPlayerSleepingPercent;
	}

	public void setMinPlayerSleepingPercent(int minPlayerSleepingPercent) {
		this.minPlayerSleepingPercent = minPlayerSleepingPercent;
	}
}
