package essentials.modules.world.time;

public class TimeWorldValues {
	private TimeWorldEnum timeWorldEnum;
	
	//SPEED_NIGHT
	private int startMinPlayerSleeping;
	
	private double speedFactor;
	private boolean useBossBar;
	
	public TimeWorldValues(TimeWorldEnum timeWorldEnum) {
		this.timeWorldEnum = timeWorldEnum;
	}

	public TimeWorldEnum getTimeWorldEnum() {
		return timeWorldEnum;
	}

	public void setTimeWorldEnum(TimeWorldEnum timeWorldEnum) {
		this.timeWorldEnum = timeWorldEnum;
	}

	public double getSpeedFactor() {
		return speedFactor;
	}

	public void setSpeedFactor(double speedFactor) {
		this.speedFactor = speedFactor;
	}

	public int getStartMinPlayerSleeping() {
		return startMinPlayerSleeping;
	}

	public void setStartMinPlayerSleeping(int startMinPlayerSleeping) {
		this.startMinPlayerSleeping = startMinPlayerSleeping;
	}

	public boolean isUseBossBar() {
		return useBossBar;
	}

	public void setUseBossBar(boolean useBossBar) {
		this.useBossBar = useBossBar;
	}
}
