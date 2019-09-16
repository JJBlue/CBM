package essentials.config;

public enum MainConfigEnum {
	FullMessage("server.fullMessage"),
	FullSize("server.showPlayerAmount"),
	JoinPlayersWhenFull("server.joinPlayersWhenFull"),
	enableOperators("server.enableOperator"),
	CouldOperators("server.couldOperator"),
	Motd("server.motd"),
	Restart("server.restart"),

	Language("plugin.language"),
	DataFolder("plugin.dataFolder"),
	lastVersionRun("plugin.lastVersionRun"),
	useVaultEconomy("plugin.useVaultEconomy"),

	bStatsEnable("bStats.enable"),
	;

	public final String value;

	MainConfigEnum(String key) {
		value = key;
	}

	public String getKey() {
		return value;
	}

	@Override
	public String toString() {
		if (value == null) return super.toString();
		return getKey();
	}
}