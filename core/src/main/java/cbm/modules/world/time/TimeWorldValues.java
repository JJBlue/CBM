package cbm.modules.world.time;

public class TimeWorldValues {
	private boolean useRealTime;

	private boolean sleep_withAFK = true;
	private int sleep_minPlayerPercent;
	private double sleep_speedFactor = 1;

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
		return sleep_speedFactor;
	}

	public void setSleepSpeedFactor(double sleepSpeedFactor) {
		this.sleep_speedFactor = sleepSpeedFactor;
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
		return sleep_minPlayerPercent;
	}

	public void setMinPlayerSleepingPercent(int minPlayerSleepingPercent) {
		this.sleep_minPlayerPercent = minPlayerSleepingPercent;
	}

	public boolean isSleepWithAFK() {
		return sleep_withAFK;
	}

	public void setSleepWithAFK(boolean sleep_withAFK) {
		this.sleep_withAFK = sleep_withAFK;
	}
}
