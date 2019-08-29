package essentials.player;

public enum PlayerConfigKey {
	nickname,
	tFly,
	tGod,
	tMute,
	loginTime,
	logoutTime,
	totalPlayTime,
	tCommandSpy,
	deathLocation,
	homeLocation,
	logoutLocation,
	teleportLocation,
	tTeleport,
	tWallGhost;
	
	private String value;
	PlayerConfigKey() {}
	PlayerConfigKey(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		if(value == null) return this.name();
		return value;
	}
}
