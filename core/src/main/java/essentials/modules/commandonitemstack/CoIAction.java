package essentials.modules.commandonitemstack;

public enum CoIAction {
	DEFAULT(null),
	LEFT("left"),
	RIGHT("right"),
	HIT("hit"), //Hit Entity
	BLOCK_BREAK("block-break"),
	BLOCK_PLACE("block-place")
	;
	
	public final String value;
	private CoIAction(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
