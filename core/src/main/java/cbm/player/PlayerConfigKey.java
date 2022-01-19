package cbm.player;

public enum PlayerConfigKey {
	nickname("nick"),
	tFly,
	tGod,
	tMute,
	loginTime,
	logoutTime,
	playTime,
	commandSpy("commandSpy"),
	tCommandSpyOperator("commandSpyOperator"),
	deathLocation,
	homeLocation,
	logoutLocation,
	tpLocation,
	tTp,
	tWallGhost,
	balance,
	joinSilent;

	private String value;

	PlayerConfigKey() {}

	PlayerConfigKey(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		if (value == null) return this.name();
		return value;
	}
}
