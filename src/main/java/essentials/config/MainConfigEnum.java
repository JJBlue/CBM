package essentials.config;

public enum MainConfigEnum {
	FullMessage("server.fullMessage"),
	FullSize("server.showPlayerAmount"),
	JoinPlayersWhenFull("server.joinPlayersWhenFull"),
	CouldOperators("server.couldOperator"),
	Motd("server.motd"),
	Language("plugin.language"),
	DataFolder("plugin.dataFolder"),
	;
	
	final String value;
	private MainConfigEnum(String key) {
		value = key;
	}
	
	public String getKey() {
		return value;
	}
	
	@Override
	public String toString() {
		if(value == null) return super.toString();
		return getKey();
	}
}
